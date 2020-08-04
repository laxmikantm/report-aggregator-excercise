
=======================================================




You may use Python, JavaScript, Java . You may use the standard library for your chosen language
freely, but not other third-party libraries. In your test code you may use common third-party
test packages (e.g. pytest, junit, testify).



Challenge - Test Aggretor
--------

Assume that we have a testing system that runs multiple tests across our
codebase. It produces output in multiple files after one or more runs, however
it does not have any way of presenting that to end users.

Your task is to write a small program that reads the files in question and
presents the output to a user in an easily consumable fashion. It should be output
to the terminal in some kind of easy-to-read way that aggregates the tests
together and is more obvious to the user what's passed and what's failed.

An example of one of the files is given here; this and four others are included
with this test:
```
--> START //src/gc:rewrite_test
=== RUN   TestRewriteFile
--- PASS: TestRewriteFile (0.01s)
PASS
```

There are several elements included in the file, such as the name of the test
the file corresponds to, individual test cases within it, timing information, etc.
Other features may appear in the other files.

An example command-line output for this test might look like:
```
//src/gc:rewrite_test 1 test run in 10ms; 1 passed
    TestRewriteFile  PASS  10ms
```
You might like to think about how developers would consume this and what
relevant information you would display (and what you would omit).
