Feature: Resource upload and parsing feature
  Scenario: hello hello test
    Given url 'http://localhost:8088/resources/40'
    When method GET
    Then status 200


  Scenario: upload resource - binary request body
    Given url 'http://localhost:8088/resources'
#    And multipart file dataStream = {read: 'classpath:tracks/f1lthy_t1mberlake.mp3'}
    And request read('classpath:tracks/f1lthy_t1mberlake.mp3')
    When method post
    Then status 200
    And def resourceId = response.id
    * java.lang.Thread.sleep(12000)

    * configure retry = {count: 5, interval: 5000}
    Given url 'http://localhost:8089/songs/resource/' + resourceId
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response.name == 'Filthy'
    And match response.artist == 'Justin Timberlake'
    And match response.length == 294
    And match response.year == '2017'



