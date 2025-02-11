package com.freez.cat.data.remote.theCatApi

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var catApiService: CatApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        catApiService = retrofit.create(CatApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getCatBreeds returns successful response`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(
                """
                [{"weight":{"imperial":"7  -  10","metric":"3 - 5"},"id":"abys","name":"Abyssinian","cfa_url":"http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx","vetstreet_url":"http://www.vetstreet.com/cats/abyssinian","vcahospitals_url":"https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian","temperament":"Active, Energetic, Independent, Intelligent, Gentle","origin":"Egypt","country_codes":"EG","country_code":"EG","description":"The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.","life_span":"14 - 15","indoor":0,"lap":1,"alt_names":"","adaptability":5,"affection_level":5,"child_friendly":3,"dog_friendly":4,"energy_level":5,"grooming":1,"health_issues":2,"intelligence":5,"shedding_level":2,"social_needs":5,"stranger_friendly":5,"vocalisation":1,"experimental":0,"hairless":0,"natural":1,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/Abyssinian_(cat)","hypoallergenic":0,"reference_image_id":"0XYvRd7oD"},{"weight":{"imperial":"7 - 10","metric":"3 - 5"},"id":"aege","name":"Aegean","vetstreet_url":"http://www.vetstreet.com/cats/aegean-cat","temperament":"Affectionate, Social, Intelligent, Playful, Active","origin":"Greece","country_codes":"GR","country_code":"GR","description":"Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.","life_span":"9 - 12","indoor":0,"alt_names":"","adaptability":5,"affection_level":4,"child_friendly":4,"dog_friendly":4,"energy_level":3,"grooming":3,"health_issues":1,"intelligence":3,"shedding_level":3,"social_needs":4,"stranger_friendly":4,"vocalisation":3,"experimental":0,"hairless":0,"natural":0,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/Aegean_cat","hypoallergenic":0,"reference_image_id":"ozEvzdVM-"},{"weight":{"imperial":"7 - 16","metric":"3 - 7"},"id":"abob","name":"American Bobtail","cfa_url":"http://cfa.org/Breeds/BreedsAB/AmericanBobtail.aspx","vetstreet_url":"http://www.vetstreet.com/cats/american-bobtail","vcahospitals_url":"https://vcahospitals.com/know-your-pet/cat-breeds/american-bobtail","temperament":"Intelligent, Interactive, Lively, Playful, Sensitive","origin":"United States","country_codes":"US","country_code":"US","description":"American Bobtails are loving and incredibly intelligent cats possessing a distinctive wild appearance. They are extremely interactive cats that bond with their human family with great devotion.","life_span":"11 - 15","indoor":0,"lap":1,"alt_names":"","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":3,"grooming":1,"health_issues":1,"intelligence":5,"shedding_level":3,"social_needs":3,"stranger_friendly":3,"vocalisation":3,"experimental":0,"hairless":0,"natural":0,"rare":0,"rex":0,"suppressed_tail":1,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/American_Bobtail","hypoallergenic":0,"reference_image_id":"hBXicehMA"},{"weight":{"imperial":"5 - 10","metric":"2 - 5"},"id":"acur","name":"American Curl","cfa_url":"http://cfa.org/Breeds/BreedsAB/AmericanCurl.aspx","vetstreet_url":"http://www.vetstreet.com/cats/american-curl","vcahospitals_url":"https://vcahospitals.com/know-your-pet/cat-breeds/american-curl","temperament":"Affectionate, Curious, Intelligent, Interactive, Lively, Playful, Social","origin":"United States","country_codes":"US","country_code":"US","description":"Distinguished by truly unique ears that curl back in a graceful arc, offering an alert, perky, happily surprised expression, they cause people to break out into a big smile when viewing their first Curl. Curls are very people-oriented, faithful, affectionate soulmates, adjusting remarkably fast to other pets, children, and new situations.","life_span":"12 - 16","indoor":0,"lap":1,"alt_names":"","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":3,"grooming":2,"health_issues":1,"intelligence":3,"shedding_level":3,"social_needs":3,"stranger_friendly":3,"vocalisation":3,"experimental":0,"hairless":0,"natural":0,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/American_Curl","hypoallergenic":0,"reference_image_id":"xnsqonbjW"},{"weight":{"imperial":"8 - 15","metric":"4 - 7"},"id":"asho","name":"American Shorthair","cfa_url":"http://cfa.org/Breeds/BreedsAB/AmericanShorthair.aspx","vetstreet_url":"http://www.vetstreet.com/cats/american-shorthair","vcahospitals_url":"https://vcahospitals.com/know-your-pet/cat-breeds/american-shorthair","temperament":"Active, Curious, Easy Going, Playful, Calm","origin":"United States","country_codes":"US","country_code":"US","description":"The American Shorthair is known for its longevity, robust health, good looks, sweet personality, and amiability with children, dogs, and other pets.","life_span":"15 - 17","indoor":0,"lap":1,"alt_names":"Domestic Shorthair","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":3,"grooming":1,"health_issues":3,"intelligence":3,"shedding_level":3,"social_needs":4,"stranger_friendly":3,"vocalisation":3,"experimental":0,"hairless":0,"natural":1,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/American_Shorthair","hypoallergenic":0,"reference_image_id":"JFPROfGtQ"},{"weight":{"imperial":"8 - 15","metric":"4 - 7"},"id":"awir","name":"American Wirehair","cfa_url":"http://cfa.org/Breeds/BreedsAB/AmericanWirehair.aspx","vetstreet_url":"http://www.vetstreet.com/cats/american-wirehair","temperament":"Affectionate, Curious, Gentle, Intelligent, Interactive, Lively, Loyal, Playful, Sensible, Social","origin":"United States","country_codes":"US","country_code":"US","description":"The American Wirehair tends to be a calm and tolerant cat who takes life as it comes. His favorite hobby is bird-watching from a sunny windowsill, and his hunting ability will stand you in good stead if insects enter the house.","life_span":"14 - 18","indoor":0,"lap":1,"alt_names":"","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":3,"grooming":1,"health_issues":3,"intelligence":3,"shedding_level":1,"social_needs":3,"stranger_friendly":3,"vocalisation":3,"experimental":0,"hairless":0,"natural":0,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/American_Wirehair","hypoallergenic":0,"reference_image_id":"8D--jCd21"},{"weight":{"imperial":"8 - 16","metric":"4 - 7"},"id":"amau","name":"Arabian Mau","vcahospitals_url":"","temperament":"Affectionate, Agile, Curious, Independent, Playful, Loyal","origin":"United Arab Emirates","country_codes":"AE","country_code":"AE","description":"Arabian Mau cats are social and energetic. Due to their energy levels, these cats do best in homes where their owners will be able to provide them with plenty of playtime, attention and interaction from their owners. These kitties are friendly, intelligent, and adaptable, and will even get along well with other pets and children.","life_span":"12 - 14","indoor":0,"alt_names":"Alley cat","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":4,"grooming":1,"health_issues":1,"intelligence":3,"shedding_level":1,"social_needs":3,"stranger_friendly":3,"vocalisation":1,"experimental":0,"hairless":0,"natural":1,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/Arabian_Mau","hypoallergenic":0,"reference_image_id":"k71ULYfRr"},{"weight":{"imperial":"7 - 15","metric":"3 - 7"},"id":"amis","name":"Australian Mist","temperament":"Lively, Social, Fun-loving, Relaxed, Affectionate","origin":"Australia","country_codes":"AU","country_code":"AU","description":"The Australian Mist thrives on human companionship. Tolerant of even the youngest of children, these friendly felines enjoy playing games and being part of the hustle and bustle of a busy household. They make entertaining companions for people of all ages, and are happy to remain indoors between dusk and dawn or to be wholly indoor pets.","life_span":"12 - 16","indoor":0,"lap":1,"alt_names":"Spotted Mist","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":4,"grooming":3,"health_issues":1,"intelligence":4,"shedding_level":3,"social_needs":4,"stranger_friendly":4,"vocalisation":3,"experimental":0,"hairless":0,"natural":0,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/Australian_Mist","hypoallergenic":0,"reference_image_id":"_6x-3TiCA"},{"weight":{"imperial":"4 - 10","metric":"2 - 5"},"id":"bali","name":"Balinese","cfa_url":"http://cfa.org/Breeds/BreedsAB/Balinese.aspx","vetstreet_url":"http://www.vetstreet.com/cats/balinese","vcahospitals_url":"https://vcahospitals.com/know-your-pet/cat-breeds/balinese","temperament":"Affectionate, Intelligent, Playful","origin":"United States","country_codes":"US","country_code":"US","description":"Balinese are curious, outgoing, intelligent cats with excellent communication skills. They are known for their chatty personalities and are always eager to tell you their views on life, love, and what you’ve served them for dinner. ","life_span":"10 - 15","indoor":0,"alt_names":"Long-haired Siamese","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":5,"grooming":3,"health_issues":3,"intelligence":5,"shedding_level":3,"social_needs":5,"stranger_friendly":5,"vocalisation":5,"experimental":0,"hairless":0,"natural":0,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":0,"wikipedia_url":"https://en.wikipedia.org/wiki/Balinese_(cat)","hypoallergenic":1,"reference_image_id":"13MkvUreZ"},{"weight":{"imperial":"4 - 9","metric":"2 - 4"},"id":"bamb","name":"Bambino","temperament":"Affectionate, Lively, Friendly, Intelligent","origin":"United States","country_codes":"US","country_code":"US","description":"The Bambino is a breed of cat that was created as a cross between the Sphynx and the Munchkin breeds. The Bambino cat has short legs, large upright ears, and is usually hairless. They love to be handled and cuddled up on the laps of their family members.","life_span":"12 - 14","indoor":0,"lap":1,"alt_names":"","adaptability":5,"affection_level":5,"child_friendly":4,"dog_friendly":5,"energy_level":5,"grooming":1,"health_issues":1,"intelligence":5,"shedding_level":1,"social_needs":3,"stranger_friendly":3,"vocalisation":3,"experimental":1,"hairless":1,"natural":0,"rare":0,"rex":0,"suppressed_tail":0,"short_legs":1,"wikipedia_url":"https://en.wikipedia.org/wiki/Bambino_cat","hypoallergenic":0,"reference_image_id":"5AdhMjeEu"}]
            """.trimIndent()
            )
            .setResponseCode(200)

        mockWebServer.enqueue(mockResponse)

        val response = catApiService.getCatBreeds()

        assertTrue(response.isSuccessful)

        val catBreeds = response.body()
        assertNotNull(catBreeds)
        assertEquals(10, catBreeds?.size)
        assertEquals("Abyssinian", catBreeds?.get(0)?.name)
    }

    @Test
    fun `test getCatBreedDetail returns successful response`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(
                """
                {
        "weight": {
            "imperial": "7  -  10",
            "metric": "3 - 5"
        },
        "id": "abys",
        "name": "Abyssinian",
        "cfa_url": "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
        "vetstreet_url": "http://www.vetstreet.com/cats/abyssinian",
        "vcahospitals_url": "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
        "temperament": "Active, Energetic, Independent, Intelligent, Gentle",
        "origin": "Egypt",
        "country_codes": "EG",
        "country_code": "EG",
        "description": "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
        "life_span": "14 - 15",
        "indoor": 0,
        "lap": 1,
        "alt_names": "",
        "adaptability": 5,
        "affection_level": 5,
        "child_friendly": 3,
        "dog_friendly": 4,
        "energy_level": 5,
        "grooming": 1,
        "health_issues": 2,
        "intelligence": 5,
        "shedding_level": 2,
        "social_needs": 5,
        "stranger_friendly": 5,
        "vocalisation": 1,
        "experimental": 0,
        "hairless": 0,
        "natural": 1,
        "rare": 0,
        "rex": 0,
        "suppressed_tail": 0,
        "short_legs": 0,
        "wikipedia_url": "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
        "hypoallergenic": 0,
        "reference_image_id": "0XYvRd7oD"
    }
            """.trimIndent()
            )
            .setResponseCode(200)

        mockWebServer.enqueue(mockResponse)

        val response = catApiService.getCatBreedDetail("abys")

        assertTrue(response.isSuccessful)

        val catBreeds = response.body()
        assertNotNull(catBreeds)
        assertEquals("Abyssinian", catBreeds?.name)
    }

    @Test
    fun `test getCatBreeds returns error response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(404)

        mockWebServer.enqueue(mockResponse)

        val response = catApiService.getCatBreeds()

        assertFalse(response.isSuccessful)
    }
}
