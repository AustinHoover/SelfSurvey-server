echo "(1)"
curl -s http://localhost:8080
echo ""
echo "(2)"
curl -c ./cookiejar -s -u username:test http://localhost:8080/login
echo ""
echo "(3)"
curl -b ./cookiejar -s http://localhost:8080/hello
echo ""
echo "(4)"
curl -b ./cookiejar -s http://localhost:8080/survey/list
echo ""
#
# Add survey -> remove survey
#
echo "(5)"
SURVEY_1=$(curl -b ./cookiejar -s http://localhost:8080/survey/add)
echo $SURVEY_1
echo ""
echo "(6)"
curl -b ./cookiejar -s http://localhost:8080/survey/list
echo ""
echo "(7)"
curl -b ./cookiejar -s http://localhost:8080/survey/delete/$SURVEY_1
echo ""
echo "(8)"
curl -b ./cookiejar -s http://localhost:8080/question/list
echo ""
#
# Add survey -> add question -> remove question -> remove survey
#
echo "(9)"
SURVEY_2=$(curl -b ./cookiejar -s http://localhost:8080/survey/add)
echo $SURVEY_2
echo ""
echo "(10)"
curl -b ./cookiejar -s http://localhost:8080/survey/list
echo ""
echo "(11)"
QUESTION_1=$(curl -b ./cookiejar -s -X POST -H "Content-Type: application/json" -d '{"type":1,"ratingMax":0,"ratingMin":10,"prompt":"test?"}' http://localhost:8080/survey/addQuestion/$SURVEY_2)
echo $QUESTION_1
echo ""
echo "(12)"
curl -b ./cookiejar -s http://localhost:8080/survey/list
echo ""
echo "(13)"
curl -b ./cookiejar -s http://localhost:8080/question/list
echo ""
echo "(14)"
curl -b ./cookiejar -s http://localhost:8080/survey/removeQuestion/$SURVEY_2/$QUESTION_1
echo ""
echo "(15)"
curl -b ./cookiejar -s http://localhost:8080/question/list
echo ""
echo "(16)"
curl -b ./cookiejar -s http://localhost:8080/survey/delete/$SURVEY_2
echo ""
echo "(17)"
curl -b ./cookiejar -s http://localhost:8080/survey/list
#
# add response -> remove response
#
echo ""
echo "(18)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/list
echo ""
echo "(19)"
RESPONSE_1=$(curl -b ./cookiejar -s http://localhost:8080/surveyresponse/add/0)
echo $RESPONSE_1
echo ""
echo "(20)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/list
echo ""
echo "(21)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/delete/$RESPONSE_1
echo ""
echo "(22)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/list
echo ""
echo "(23)"
#
# add response -> add response value -> remove response value -> remove response
#
echo ""
echo "(24)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/list
echo ""
echo "(25)"
RESPONSE_2=$(curl -b ./cookiejar -s http://localhost:8080/surveyresponse/add/0)
echo $RESPONSE_2
echo ""
echo "(26)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/list
echo ""
echo "(27)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/value/list
echo ""
echo "(28)"
VALUE_1=$(curl -b ./cookiejar -s -X POST -H "Content-Type: application/json" -d '{"questionId":1,"value":"test"}' http://localhost:8080/surveyresponse/value/add/$RESPONSE_2)
echo $VALUE_1
echo ""
echo "(29)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/value/list
echo ""
echo "(30)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/value/remove/$RESPONSE_2/$VALUE_1
echo ""
echo "(31)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/value/list
echo ""
echo "(32)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/delete/$RESPONSE_2
echo ""
echo "(33)"
curl -b ./cookiejar -s http://localhost:8080/surveyresponse/list
echo ""
echo "(34)"


echo "Cleaning up.."
rm ./cookiejar