<h1 align="center">
  🕸😎🐐
  <br />
  Groffline
</h1>


## What is Groff?
In short, a typesetting system. See [the homepage](https://www.gnu.org/software/groff/).

## What is Groffline?
Groffline is groff in the browser. It consists of a browser frontend and an API for generating PDFs.

## API
The backend responds to HTTP request on the path `/groffline`. It takes two 
parameters, the macro set to be used and the actual input to groff. An example 
could be `/groffline?macro=ms&input=.TL%0ATitle`. Notice that the input needs 
to be URI/URL encoded. The possible macros are 

* ms
* mm
* me 
* mom

The *table* and *equation* pre-processors are always used.

The api was built using the [Spring (boot) framework](https://spring.io/).


## Frontend
The frontend is some HTML and javascript glued together. It uses the great 
[Ace editor](https://ace.c9.io/) for editing. [pdf.js](https://mozilla.github.io/pdf.js/) is used to
show PDFs. It is served by the Apache Tomcat, through the Spring framework.

## Running
First obtain a copy of `pdf.js` from their [website](https://mozilla.github.io/pdf.js/getting_started/#download).
Extract it and put it in as `src/main/resources/static/pdfjs` (get rid of the version part of the directory name).

The project is built/run with Gradle. It comes with a gradle wrapper as well, 
so to run the project, just run `./gradlew bootRun`. The application will then
be running on port 8080. Alternatively, a `jar` can be built by `./gradlew bootJar`
and deployed elsewhere.

## License 
The project is licensed under the GPL-3.0 License.
