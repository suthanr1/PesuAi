import express from "express";
import cors from "cors";
import OpenAI from "openai";

const app = express();
app.use(cors());
app.use(express.json());

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

app.post("/correct", async (req,res)=>{
  try {
    const text = String(req.body.text || "").trim();
    if (!text) return res.status(400).json({error:"text_required"});
    const response = await client.responses.create({
      model: "gpt-5.6-luna",
      instructions:
        "You are PesuAI, a friendly English tutor for Tamil speakers. " +
        "Return concise learner feedback in Tamil. Give: " +
        "1) Correct English, 2) Tamil meaning, 3) one short grammar explanation, " +
        "4) one natural alternative. If the input is already correct, say so.",
      input: text
    });
    res.json({answer: response.output_text});
  } catch (e) {
    console.error(e);
    res.status(500).json({error:"ai_request_failed"});
  }
});

app.get("/health",(req,res)=>res.json({ok:true}));
app.listen(3000,()=>console.log("PesuAI backend running on :3000"));
