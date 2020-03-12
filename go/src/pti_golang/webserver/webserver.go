package main

import (
    "fmt"
    "log"
    "net/http"
    "github.com/gorilla/mux"
    "encoding/json"
    "io"
    "io/ioutil"
    "encoding/csv"
    "os"
)

type ResponseMessage struct {
    Model string
    Brand string
    Days string
    Name string
}

type RequestMessage struct {
    Model string
    Brand string
    Days string
    Name string
}

func main() {

router := mux.NewRouter().StrictSlash(true)
router.HandleFunc("/", Index)
router.HandleFunc("/endpoint/{param}", endpointFunc)
router.HandleFunc("/endpoint2/{param}", endpointFunc2JSONInput)

log.Fatal(http.ListenAndServe(":8080", router))
}

func writeToFile(w http.ResponseWriter, values []string) {
    file, err := os.OpenFile("rentals.csv", os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0600)
    if err!=nil {
        json.NewEncoder(w).Encode(err)
        return
        }
    writer := csv.NewWriter(file)
    writer.Write(values)
    writer.Flush()
    file.Close()
}

func Index(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintln(w, "Service OK")
}

func endpointFunc(w http.ResponseWriter, r *http.Request) {
	var rentalsArray []ResponseMessage
	file, err := os.Open("rentals.csv", )
	if err != nil {
	    log.Fatal(err)
	} 
	reader := csv.NewReader(file)
	for {
	    line, err := reader.Read()
	    if err != nil {
	        if err == io.EOF {
	            break
	        }
	        log.Fatal(err)
	    }
	    rentalsArray = append(rentalsArray, ResponseMessage{Name: line[0], Model: line[1], Days: line[2],Brand:line[3]})
	}
	json.NewEncoder(w).Encode(rentalsArray)
}

func endpointFunc2JSONInput(w http.ResponseWriter, r *http.Request) {
    var requestMessage RequestMessage
    body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
    if err != nil {
        panic(err)
    }
    if err := r.Body.Close(); err != nil {
        panic(err)
    }
    if err := json.Unmarshal(body, &requestMessage); err != nil {
        w.Header().Set("Content-Type", "application/json; charset=UTF-8")
        w.WriteHeader(422) // unprocessable entity
        if err := json.NewEncoder(w).Encode(err); err != nil {
            panic(err)
        }
    } else {
        fmt.Fprintln(w, "Customer ", requestMessage.Name, ", the price for your rental will be 689€")
        fmt.Println(r.FormValue("queryparam1"))
    }
    writeToFile(w, []string{requestMessage.Name ,requestMessage.Brand, requestMessage.Model, requestMessage.Days})
}

