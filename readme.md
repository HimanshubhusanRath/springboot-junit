## Usage of `tag`:
* We can mark the test class / test method with a tag value e.g. `@Tag("dev")`
* Now in the suite class, we can define `@IncludeTags("dev")` to run only those tests which are marked with 'dev' tag (mentioned above)