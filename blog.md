# Project notes

## 2026-10-05 — Local AI investigation for payout approvals

- Added local Qwen via Ollama to draft advisory briefs for manual approval runs. Model calls are bounded to 14 seconds at the HTTP layer and Temporal does not retry them; failures fall back to human review.
- The model can choose zero, one, or two read-only synthetic lookups. Each lookup is a Temporal Activity. Empty review facts skip investigation deterministically, and `MarkInvestigationSkipped` records a no-lookup decision in Event History.
- Scenario controls now accept synthetic review facts and select between two synthetic customer records. Successful payout defaults to “typical customer behavior”; the other scenarios default to “new recipient” and “unusual amount for this customer.”
- Live Qwen runs initially made unsupported claims about amount units and recipient verification. The brief now keeps numeric amounts in typed UI fields, validates referenced facts, and uses fixed notes for the two default flags. Synthetic lookup data cannot establish fraud, and the recommendation never approves or rejects a payout.
- The local build and approval/replay tests passed during development. Live manual approval runs exercised both the two-lookup path and the no-lookup timeline marker. These runs were functional checks, not latency benchmarks.
- Hosted model selection remains future work. `DEMO_AI_MODEL` currently changes the local Ollama model without a UI toggle.
