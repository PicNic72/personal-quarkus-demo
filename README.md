# quarkus-demo

A minimal [Quarkus](https://quarkus.io/) 3.x REST application that demonstrates integrating four common Java libraries — Woodstox, JsonPath, org.json, and Spring Core utilities — inside a reactive JAX-RS endpoint.

## Requirements

| Tool | Version |
|------|---------|
| Java | 17+ |
| Maven | 3.8+ (or use the included `./mvnw` wrapper) |

## Project structure

```
src/
└── main/
    ├── java/com/example/
    │   ├── DemoApplication.java   # @QuarkusMain entry point
    │   └── DemoResource.java      # GET /demo REST endpoint
    └── resources/
        └── application.properties # Port, app name, log level
```

## Running in dev mode

Dev mode enables live reload on every save.

```bash
./mvnw quarkus:dev
```

The application starts on **http://localhost:8081**.

## Building

```bash
./mvnw package
```

This produces a runnable JAR under `target/quarkus-app/`.

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

## API

### `GET /demo`

Exercises all four dependencies and returns a JSON payload.

**Response example**

```json
{
  "xmlMessage": "Hello from Woodstox",
  "jsonPathResult": "Quarkus in Action",
  "springCapitalized": "Quarkus in action",
  "status": "ok"
}
```

| Field | Source library | Description |
|-------|---------------|-------------|
| `xmlMessage` | `woodstox-core` 6.0.3 | First text node extracted from a sample XML snippet via the Woodstox StAX factory |
| `jsonPathResult` | `json-path` 2.8.0 | Book title extracted from a sample JSON string using `$.store.book.title` |
| `springCapitalized` | `spring-core` 5.3.18 | `StringUtils.capitalize()` applied to the JSONPath result |
| `status` | `org.json` 20220320 | Response object assembled with `JSONObject` |

## Key dependencies

| Artifact | Version | Purpose |
|----------|---------|---------|
| `io.quarkus.platform:quarkus-bom` | 3.8.4 | Quarkus BOM — manages all Quarkus extension versions |
| `io.quarkus:quarkus-resteasy-reactive-jackson` | managed | Reactive JAX-RS + Jackson JSON serialisation |
| `io.quarkus:quarkus-arc` | managed | CDI dependency injection |
| `com.fasterxml.woodstox:woodstox-core` | 6.0.3 | High-performance StAX XML streaming parser |
| `com.jayway.jsonpath:json-path` | 2.8.0 | JSONPath expression evaluation |
| `org.json:json` | 20220320 | Lightweight JSON object construction |
| `org.springframework:spring-core` | 5.3.18 | String and collection utility classes |

## Configuration

Configured via [`src/main/resources/application.properties`](src/main/resources/application.properties).

| Property | Default | Description |
|----------|---------|-------------|
| `quarkus.http.port` | `8081` | HTTP listener port |
| `quarkus.application.name` | `quarkus-demo` | Application name reported in logs |
| `quarkus.log.level` | `INFO` | Root log level |

## Running tests

```bash
./mvnw test
```
