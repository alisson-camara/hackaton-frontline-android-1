# ktor-bff-training
Repository created to learn ktor heroku integration

URL: https://hackaton-frontline-android-1-1387e168fe2f.herokuapp.com/ 

Exemplos de Requests: =====================

URL: {BASE_URL}/room?room=SalaDaPaula
Method: GET
Protocol: http/1.1
Status: Complete
Response: 200 OK
SSL: Yes


---------- Request ----------

(body is empty)

---------- Response ----------

{
  "name": "SalaDaPaula",
  "currentTask": "Task do Alisson",
  "moderator": "Paula",
  "players": [
    {
      "name": "Paula",
      "point": "3"
    },
    {
      "name": "Alisson",
      "point": "34"
    },
    {
      "name": "UltimoPlayer",
      "point": "?"
    },
    {
      "name": "UltimoDosMoicanos",
      "point": "?"
    }
  ]
}

====================================================

URL: {BASE_URL}/create-room?room=SalaDaPaula&moderator=Paula
Method: POST
Protocol: http/1.1
Status: Complete
Response: 200 OK
SSL: Yes

---------- Request ----------

(body is empty)

---------- Response ----------

{
  "name": "SalaDaPaula",
  "currentTask": "Task 1",
  "moderator": "Paula",
  "players": [
    {
      "name": "Paula",
      "point": "?"
    }
  ]
}

===================================================================



URL: {BASE_URL}/remove-player?room=SalaDaPaula&player=Paula
Method: POST
Protocol: http/1.1
Status: Complete
Response: 200 OK
SSL: Yes


---------- Request ----------

Paula

---------- Response ----------

{
  "name": "SalaDaPaula",
  "currentTask": "Task 1",
  "moderator": "Paula",
  "players": []
}

===================================================================


URL: {BASE_URL}/reset-votes?room=SalaDaPaula&player=Paula
Method: POST
Protocol: http/1.1
Status: Complete
Response: 200 OK
SSL: Yes

---------- Request ----------


(body is empty)

---------- Response ----------


{
  "name": "SalaDaPaula",
  "currentTask": "Task 1",
  "moderator": "Paula",
  "players": [
    {
      "name": "Paula",
      "point": "?"
    }
  ]
}

=========================================================================

URL: {BASE_URL}/sendvote?room=SalaDaPaula&player=Paula
Method: POST
Protocol: http/1.1
Status: Complete
Response: 200 OK
SSL: Yes

---------- Request ----------


3

---------- Response ----------

{
  "name": "SalaDaPaula",
  "currentTask": "Task 1",
  "moderator": "Paula",
  "players": [
    {
      "name": "Paula",
      "point": "3"
    }
  ]
}


==========================================================

URL: {BASE_URL}/join-room?room=SalaDaPaula&player=Paula
Method: POST
Protocol: http/1.1
Status: Complete
Response: 200 OK
SSL: Yes

---------- Request ----------


(body is empty)

---------- Response ----------

{
  "name": "SalaDaPaula",
  "currentTask": "Task 1",
  "moderator": "Paula",
  "players": [
    {
      "name": "Paula",
      "point": "?"
    }
  ]
}
