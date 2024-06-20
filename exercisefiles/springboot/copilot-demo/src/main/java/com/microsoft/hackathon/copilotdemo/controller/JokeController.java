package com.microsoft.hackathon.copilotdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JokeController {

    @GetMapping("/joke")
    @ResponseBody
    public String jokePage() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Chuck Norris Joke</title>
                </head>
                <body>
                    <h2>Chuck Norris Joke</h2>
                    <button onclick="fetchJoke()">New Joke</button>
                    <p id="jokeText"></p>
                    <img id="jokeIcon" src="" alt="Joke Icon" style="display:none;">
                    <script>
                        async function fetchJoke() {
                            try {
                                const response = await fetch('https://api.chucknorris.io/jokes/random');
                                const data = await response.json();
                                document.getElementById('jokeText').textContent = data.value;
                                const jokeIcon = document.getElementById('jokeIcon');
                                jokeIcon.src = data.icon_url;
                                jokeIcon.style.display = 'block';
                            } catch (error) {
                                console.error('Error fetching joke:', error);
                                document.getElementById('jokeText').textContent = 'Failed to fetch joke.';
                                document.getElementById('jokeIcon').style.display = 'none';
                            }
                        }
                    </script>
                <br><br>
                Click <a href=\"/\">here</a> to back to the main page.
                </body>
                </html>
                """;
    }
}