package main

import (
	"context"
	"log"
	"net/http"
	"os"

	firebase "firebase.google.com/go/v4"
	"firebase.google.com/go/v4/messaging"
	"google.golang.org/api/option"
)

func main() {
	//log.SetOutput(ioutil.Discard) //отключаем логи
	port := "3130"
	if len(os.Args) > 1 {
		port = os.Args[1]
	}

	err := os.Setenv("tls13", "1")
	if err != nil {
		log.Println(err.Error())
	}
	log.Println("Запуск веб-сервера на http://127.0.0.1:" + port)
	customHandler := CustomHandler{}
	err2 := http.ListenAndServe(":"+port, customHandler)
	log.Fatal(err2)

}

type CustomHandler struct {
}

// The implementaiton of the ServeHTTP function that handle http requests
func (c CustomHandler) ServeHTTP(responseWriter http.ResponseWriter, request *http.Request) {
	if request.URL.Path == "/custom" {
		//log.Println("custom")
	} else {
		//log.Println("all")
	}
	push(responseWriter, request)
}

func push(responseWriter http.ResponseWriter, request *http.Request) {
	// Инициализация Firebase Admin SDK.

	ctx := context.Background()
	opt := option.WithCredentialsFile("C:/Users/vladg/OneDrive/Документы/мои доки/PushTest/serviceAccountKey.json")
	app, err := firebase.NewApp(ctx, nil, opt)
	if err != nil {
		log.Fatalf("Error initializing Firebase app: %v\n", err)
	}

	// Получение клиента для отправки уведомлений.
	client, err := app.Messaging(ctx)
	if err != nil {
		log.Fatalf("Error getting Firebase Messaging client: %v\n", err)
	}

	// Отправка уведомления.
	message := &messaging.Message{
		Notification: &messaging.Notification{
			Title: request.Header["Title"][0],
			Body:  request.Header["Body"][0],
		},
		Data: map[string]string{
			"key": "value",
		},
		Token: request.Header["Token"][0],
	}
	response, err := client.Send(ctx, message)
	if err != nil {
		log.Fatalf("Error sending message: %v\n", err)
	}
	log.Printf("Successfully sent message: %v\n", response)
}
