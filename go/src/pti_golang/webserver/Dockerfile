FROM ubuntu:latest
WORKDIR /
RUN apt-get update
RUN apt-get -y install curl
RUN apt-get -y install wget
RUN apt-get -y install git
RUN wget https://gitlab.fib.upc.edu/pti/pti/raw/master/goREST/go1.7.1.linux-amd64.tar.gz
RUN tar -C /usr/local -xzf go1.7.1.linux-amd64.tar.gz
ARG PATH=$PATH:/usr/local/go/bin
RUN mkdir /go
ARG GOPATH=/go
RUN mkdir /go/src
RUN mkdir -p /go/src/pti_golang/webserver
COPY webserver.go $HOME/go/src/pti_golang/webserver
RUN /usr/local/go/bin/go get "github.com/gorilla/mux"
RUN /usr/local/go/bin/go install pti_golang/webserver
EXPOSE 8080
CMD /go/bin/webserver
