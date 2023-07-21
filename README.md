# jwc
Java implementation of Linux wc utility

## Usage
```agsl
jwc -wlcb file.txt
jwc --words file.txt
jwc --lines file.txt
jwc --lines --words file.txt
jwc -wc --lines file.txt
```

## How to build the project
```agsl
./gradlew shadowJarc
alias jwc='java -jar build/libs/jwc-shadow.jar'
```
