# PesuAI V1.1 — AI Typing MVP

## What is included
- Android Home screen
- Type with AI screen
- Secure backend pattern
- OpenAI Responses API integration
- Tamil explanations for English corrections

## Run backend
1. Install Node.js 20+
2. `cd backend`
3. `npm install`
4. Set `OPENAI_API_KEY` as an environment variable.
5. `npm start`

Do NOT put the OpenAI API key inside the Android app.

For Android Emulator, the backend URL is `http://10.0.2.2:3000`.
For a physical phone, replace it with the computer's LAN IP (for example `http://192.168.x.x:3000`) and allow the port through the firewall.

## Next
Add authentication, rate limits, database, speech-to-text, text-to-speech, and production HTTPS before public launch.
