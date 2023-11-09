# Sage

This is a JDA discord bot that can be used as a FAQ or help bot, it doesn't need to recompile to update the Manual.


## Manual

Manual is a Sage feature that gets information from the file "etc/manual.md". Manual uses Markdown, this is an example of `manual.md` content:

```markdown
[: hello-world-java - Hello world program in Java :] {:
    ### How to make a hello world program in Java?
    **Hello world program in Java:**
    ```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}```
    You can use `java` to compile and run your program.
:}
    
[: hello-world-rust - Hello world program in Rust :] {:
    ### How to make a hello world program in Rust?
    **Hello world program in Rust:**
    ```rust
fn main() {
    println!("Hello, world!");
}```
    You can use `rustc` to compile the program.
:}
```


![Sage screenshot](/img/showcase.png)

## How to start bot

1. Make sure you have Java >= 17 installed: Type `java -version` to check if its installed on your system.

2. On the [Releases](https://github.com/ninsix/sage-jda/releases) section download the 7z, then extract it.

3. To start **Sage**, go to bin/ folder, then enter on the terminal:

For Linux, Mac, BSD or other unix: 
```
./sage
```

For Windows: 
```
sage.bat
```

## How to compile Sage

1. Make sure you have Java JDK >= 17 installed. You can install it from your package manager or from [Azul Systems](https://azul.com/downloads).

2.

For Linux, Mac, BSD or other unix: 
```
git clone https://github.com/ninsix/bB-discord-bot/
cd sage-jda/
chmod +x gradlew
./gradlew build
```

For Windows: 
Download the source code zip, extract it. In the folder, enter on the terminal:
```
gradlew.bat build
```

The build is saved in sage-jda/
