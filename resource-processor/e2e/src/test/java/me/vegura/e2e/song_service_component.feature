Feature: Song service storing song metadata
  Scenario:
    Given url 'http://localhost:8089'
    And path 'songs'
    And def song_name = 'Take A Chance On Me'
    And def song_artist = 'ABBA'
    And def song_album = 'The Album'
    And def song_length = 200
    And def song_resource_id = '1098'
    And def song_year = '2001'
    And request {name: 'Take A Chance On Me', artist: 'ABBA', album: 'The Album', length: 200, resourceId: '1098', year: '2001'}
    When method post
    Then status 200
    And match response == {id: '#number'}
    And def song_id = response.id

    * configure retry = {count: 5, interval: 5000}
    Given url 'http://localhost:8089/songs/' + song_id
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response.name == song_name
    And match response.artist == song_artist