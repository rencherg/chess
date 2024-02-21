# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922

```


Phase 2 Diagram Links:
Default
https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAOLAC2KF1hKd+hYsFIAlFAHMkAZzBRgBCGm5Ve-BkNIARFAhRgulFSD64BjYcwAyESUsOrT6pjGviIAVzDKH9Qc83ywACCIMZSUpgAJoEARsBSKDCRMZiY4lAe2DAAxGjAVACeMGKSMnIKaDkA7gAWSPqYiKikALQAfOSGlABcMADaAAoA8mQAKgC6MAD07glQADpoAN6zlHmcADQwuOFV0JFbKOzASAgAvpjKlDDtxRLSsvJIit7GvVD3MpQAFKtQ6ygtjspHsoAcYEcTggAJSYEoPcrPOw8Yw3DoBMDBUIocK9cR6ACqc1+cwBsIxWLCUjRSRivTIAFFLAy4KMYH8ATAAGYZdjsuaYCkhKk3W7wspPF72N4wNDuBAIOGfR4VV6JW5C7G4mAgD7yFBEn4cjiA7bxEH7cmBYU46m3ZK9ACSADlGSI2cbNmbdvtDsdTjAXaMhvzaOKVUi1TTNVTerqUPqgp4aiS1iarZibeEaQ7A66Ge7Q-8TVtgMnRhAANboPPBmBlsA1QXWrV2sXKxFSlEoXoNmoV6toJWlCNdozq25XKC98tV9CXLqwFq3JroMC9ABMAAYt4sln2B+gLuhIql0pkctBhPiXDYkJVsrV6nxV60OlPeoMRhNpglwki909U1gVBSILinGlXHvNVegQO80FTYsvRAy1MCg5Fx2jFtYxgfEwENKBEIBIFzVAjNKVtHM6XIJkWQ9UkS29C0wW5Xki2bTNW1FDp0Jg9jeOldV0Ww204z1fQk0bIj0w4ijs3taiXTdei0y9A850qIMQz7WSszbHj4L49TBzQwzBO4zpqB6etZxMiDlw6V91xgbdd2WYyj0wE8zwydwsmydIUBrVwPGYR86gaJyaQ-fpNFo0YGUmKY-ykAC8ls+d7IMtxPD4uCcrAb4PLQWEQty8yNRE7VIh0PQUEklNivIvSqN6OLmQSmzG0PSoeQgPkdPsldkDXTcdy8tBTzSXz-I+SIXAeVgTWpcLn0aEa30smhp36FgGR-KZxBNRZioXKylw6NhOD4vCrpQIqMpKzA7qjSrOJwvCGoe7qNOariFPpWjWS6-sNNY-qQd0-7bhewTej+Z6TVe4T3tE3C9Du74-pFAGaOZYG+r5I7OChnH2lh7teiWPolmJlBHU0LYn30AjiJgGIEFAStWcYunnRNM4YHGC4Kcwhytus6naZNBmmYig0GK9DmuZ5r0+YFoXwMXCynLGtypbp2WYGZhXVNNZWQG5xXTXVzhBeFiapvPPycmwdwoGwXR4HExI7uqeX1uaZhJ0XT9hjGJK6ZOx691tlAtfOmlRd4MSE30THiq2OPYWT1E3rkqQ8T0L6mtJyjccZfG2WK8GBuTMv5JhpG4fY3OhJgGM0fjfVMezhu7Q6XMlILNk49rEM6f77i26pw3NBF5vuwsmLpc4BmE+2nWNuc1zY5l+fHZ8i9sgKHQ4KqGAACkIHvJbOByC3K0DtdotD-oBgJA6o-Sn7Bz3Jy4AQDglALO+8N7XCbtdFuAArG+aAM6PS2CAbegDgGgLXpoHOi8xYowLkXMAJdHrY3LoPailc6Igx6rXSGndG6XWwSnVuDC864L0vDbA0R04mm+MgoOqDoDoPppgqeuMCQDE0EETqdMaJsl4WufhsAADqAAJAsDJcL7zOpvcWusXLjW8tNY+6RgBFEQAmWAwBsCe0IJKB8Jtn6bRin0dq+1ErTGSFoiB6Jar6D4skLGgofEGCXvnNhtIAm0IHrSNq8V1EADERBDAALIwAahSAA3CI0hMSOrxMSSkgiGSsnRI7rEmACTkl3xQEUoajlt560PkAA

Presentation Mode
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAOLAC2KF1hKd+hYsFIAlFAHMkAZzBRgBCGm5Ve-BkNIARFAhRgulFSD64BjYcwAyESUsOrT6pjGviIAVzDKH9Qc83ywACCIMZSUpgAJoEARsBSKDCRMZiY4lAe2DAAxGjAVACeMGKSMnIKaDkA7gAWSPqYiKikALQAfOSGlABcMADaAAoA8mQAKgC6MAD07glQADpoAN6zlHmcADQwuOFV0JFbKOzASAgAvpjKlDDtxRLSsvJIit7GvVD3MpQAFKtQ6ygtjspHsoAcYEcTggAJSYEoPcrPOw8Yw3DoBMDBUIocK9cR6ACqc1+cwBsIxWLCUjRSRivTIAFFLAy4KMYH8ATAAGYZdjsuaYCkhKk3W7wspPF72N4wNDuBAIOGfR4VV6JW5C7G4mAgD7yFBEn4cjiA7bxEH7cmBYU46m3ZK9ACSADlGSI2cbNmbdvtDsdTjAXaMhvzaOKVUi1TTNVTerqUPqgp4aiS1iarZibeEaQ7A66Ge7Q-8TVtgMnRhAANboPPBmBlsA1QXWrV2sXKxFSlEoXoNmoV6toJWlCNdozq25XKC98tV9CXLqwFq3JroMC9ABMAAYt4sln2B+gLuhIql0pkctBhPiXDYkJVsrV6nxV60OlPeoMRhNpglwki909U1gVBSILinGlXHvNVegQO80FTYsvRAy1MCg5Fx2jFtYxgfEwENKBEIBIFzVAjNKVtHM6XIJkWQ9UkS29C0wW5Xki2bTNW1FDp0Jg9jeOldV0Ww204z1fQk0bIj0w4ijs3taiXTdei0y9A850qIMQz7WSszbHj4L49TBzQwzBO4zpqB6etZxMiDlw6V91xgbdd2WYyj0wE8zwydwsmydIUBrVwPGYR86gaJyaQ-fpNFo0YGUmKY-ykAC8ls+d7IMtxPD4uCcrAb4PLQWEQty8yNRE7VIh0PQUEklNivIvSqN6OLmQSmzG0PSoeQgPkdPsldkDXTcdy8tBTzSXz-I+SIXAeVgTWpcLn0aEa30smhp36FgGR-KZxBNRZioXKylw6NhOD4vCrpQIqMpKzA7qjSrOJwvCGoe7qNOariFPpWjWS6-sNNY-qQd0-7bhewTej+Z6TVe4T3tE3C9Du74-pFAGaOZYG+r5I7OChnH2lh7teiWPolmJlBHU0LYn30AjiJgGIEFAStWcYunnRNM4YHGC4Kcwhytus6naZNBmmYig0GK9DmuZ5r0+YFoXwMXCynLGtypbp2WYGZhXVNNZWQG5xXTXVzhBeFiapvPPycmwdwoGwXR4HExI7uqeX1uaZhJ0XT9hjGJK6ZOx691tlAtfOmlRd4MSE30THiq2OPYWT1E3rkqQ8T0L6mtJyjccZfG2WK8GBuTMv5JhpG4fY3OhJgGM0fjfVMezhu7Q6XMlILNk49rEM6f77i26pw3NBF5vuwsmLpc4BmE+2nWNuc1zY5l+fHZ8i9sgKHQ4KqGAACkIHvJbOByC3K0DtdotD-oBgJA6o-Sn7Bz3Jy4AQDglALO+8N7XCbtdFuAArG+aAM6PS2CAbegDgGgLXpoHOi8xYowLkXMAJdHrY3LoPailc6Igx6rXSGndG6XWwSnVuDC864L0vDbA0R04mm+MgoOqDoDoPppgqeuMCQDE0EETqdMaJsl4WufhsAADqAAJAsDJcL7zOpvcWusXLjW8tNY+6RgBFEQAmWAwBsCe0IJKB8Jtn6bRin0dq+1ErTGSFoiB6Jar6D4skLGgofEGCXvnNhtIAm0IHrSNq8V1EADERBDAALIwAahSAA3CI0hMSOrxMSSkgiGSsnRI7rEmACTkl3xQEUoajlt560PkAA

Presentation Shrink to Fit
https://sequencediagram.org/index.html?presentationMode=readOnly&shrinkToFit=true#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAOLAC2KF1hKd+hYsFIAlFAHMkAZzBRgBCGm5Ve-BkNIARFAhRgulFSD64BjYcwAyESUsOrT6pjGviIAVzDKH9Qc83ywACCIMZSUpgAJoEARsBSKDCRMZiY4lAe2DAAxGjAVACeMGKSMnIKaDkA7gAWSPqYiKikALQAfOSGlABcMADaAAoA8mQAKgC6MAD07glQADpoAN6zlHmcADQwuOFV0JFbKOzASAgAvpjKlDDtxRLSsvJIit7GvVD3MpQAFKtQ6ygtjspHsoAcYEcTggAJSYEoPcrPOw8Yw3DoBMDBUIocK9cR6ACqc1+cwBsIxWLCUjRSRivTIAFFLAy4KMYH8ATAAGYZdjsuaYCkhKk3W7wspPF72N4wNDuBAIOGfR4VV6JW5C7G4mAgD7yFBEn4cjiA7bxEH7cmBYU46m3ZK9ACSADlGSI2cbNmbdvtDsdTjAXaMhvzaOKVUi1TTNVTerqUPqgp4aiS1iarZibeEaQ7A66Ge7Q-8TVtgMnRhAANboPPBmBlsA1QXWrV2sXKxFSlEoXoNmoV6toJWlCNdozq25XKC98tV9CXLqwFq3JroMC9ABMAAYt4sln2B+gLuhIql0pkctBhPiXDYkJVsrV6nxV60OlPeoMRhNpglwki909U1gVBSILinGlXHvNVegQO80FTYsvRAy1MCg5Fx2jFtYxgfEwENKBEIBIFzVAjNKVtHM6XIJkWQ9UkS29C0wW5Xki2bTNW1FDp0Jg9jeOldV0Ww204z1fQk0bIj0w4ijs3taiXTdei0y9A850qIMQz7WSszbHj4L49TBzQwzBO4zpqB6etZxMiDlw6V91xgbdd2WYyj0wE8zwydwsmydIUBrVwPGYR86gaJyaQ-fpNFo0YGUmKY-ykAC8ls+d7IMtxPD4uCcrAb4PLQWEQty8yNRE7VIh0PQUEklNivIvSqN6OLmQSmzG0PSoeQgPkdPsldkDXTcdy8tBTzSXz-I+SIXAeVgTWpcLn0aEa30smhp36FgGR-KZxBNRZioXKylw6NhOD4vCrpQIqMpKzA7qjSrOJwvCGoe7qNOariFPpWjWS6-sNNY-qQd0-7bhewTej+Z6TVe4T3tE3C9Du74-pFAGaOZYG+r5I7OChnH2lh7teiWPolmJlBHU0LYn30AjiJgGIEFAStWcYunnRNM4YHGC4Kcwhytus6naZNBmmYig0GK9DmuZ5r0+YFoXwMXCynLGtypbp2WYGZhXVNNZWQG5xXTXVzhBeFiapvPPycmwdwoGwXR4HExI7uqeX1uaZhJ0XT9hjGJK6ZOx691tlAtfOmlRd4MSE30THiq2OPYWT1E3rkqQ8T0L6mtJyjccZfG2WK8GBuTMv5JhpG4fY3OhJgGM0fjfVMezhu7Q6XMlILNk49rEM6f77i26pw3NBF5vuwsmLpc4BmE+2nWNuc1zY5l+fHZ8i9sgKHQ4KqGAACkIHvJbOByC3K0DtdotD-oBgJA6o-Sn7Bz3Jy4AQDglALO+8N7XCbtdFuAArG+aAM6PS2CAbegDgGgLXpoHOi8xYowLkXMAJdHrY3LoPailc6Igx6rXSGndG6XWwSnVuDC864L0vDbA0R04mm+MgoOqDoDoPppgqeuMCQDE0EETqdMaJsl4WufhsAADqAAJAsDJcL7zOpvcWusXLjW8tNY+6RgBFEQAmWAwBsCe0IJKB8Jtn6bRin0dq+1ErTGSFoiB6Jar6D4skLGgofEGCXvnNhtIAm0IHrSNq8V1EADERBDAALIwAahSAA3CI0hMSOrxMSSkgiGSsnRI7rEmACTkl3xQEUoajlt560PkAA
