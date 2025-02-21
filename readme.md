## Usage of `tag`:
* We can mark the test class / test method with a tag value e.g. `@Tag("dev")`
* Now in the suite class, we can define `@IncludeTags("dev")` to run only those tests which are marked with 'dev' tag (mentioned above)

## WireMock:
* To mock external API calls, Wiremock can be used.
* Here we define the stubs for the api calls and assert the behaviour.

### RestTemplate in test cases
* ✅ Use @Mock when you want to isolate the test from actual API calls. [Unit Testing]
* ✅ Use @Autowired when you want to interact with WireMock and test real HTTP requests. [Integration Testing]