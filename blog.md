# Project notes

## 2026-10-05 — Local AI investigation for payout approvals

- Added local Qwen via Ollama to draft advisory briefs for manual approval runs. Model calls are bounded to 14 seconds at the HTTP layer and Temporal does not retry them; failures fall back to human review.
- The model can choose zero, one, or two read-only synthetic lookups. Each lookup is a Temporal Activity. Empty review facts skip investigation deterministically, and `MarkInvestigationSkipped` records a no-lookup decision in Event History.
- Scenario controls now accept synthetic review facts and select between two synthetic customer records. Successful payout defaults to “typical customer behavior”; the other scenarios default to “new recipient” and “unusual amount for this customer.”
- Live Qwen runs initially made unsupported claims about amount units and recipient verification. The brief now keeps numeric amounts in typed UI fields, validates referenced facts, and uses fixed notes for the two default flags. Synthetic lookup data cannot establish fraud, and the recommendation never approves or rejects a payout.
- The local build and approval/replay tests passed during development. Live manual approval runs exercised both the two-lookup path and the no-lookup timeline marker. These runs were functional checks, not latency benchmarks.
- Hosted model selection remains future work. `DEMO_AI_MODEL` currently changes the local Ollama model without a UI toggle.

## 2026-10-06 — AI brief on low-value demo payouts

- Startup now displays Compose progress and an elapsed-time update every five seconds while Docker is quiet. A 20-second Docker Engine readiness check stops an unresponsive startup rather than reporting the containers as up. A local daemon probe timed out; a later live `make start` completed with the new progress output.
- Two manual $75 runs had `aiBriefEnabled=true` and review facts but no AI Activities because the workflow entered the AI branch only above the $500 human-approval threshold.
- Manually started demo payouts now draft the AI brief after FX validation at any amount. Payouts below the threshold still skip human approval; load-simulator runs still skip AI. Existing histories without the version marker replay on their original path.
- Unit tests passed for a successful low-value payout, a permanent rail rejection after investigation, and the existing replay suite. Live runs `payout-successful-po-01a111aa0d7874f9` and `payout-permanent-po-01a111aa0d7874fa` recorded ChooseInvestigation and DraftAiBrief before their respective completion and rail rejection, with available Qwen briefs.
- The original successful run was saved as a 52-event replay fixture. The replay suite passed with it, confirming that a pre-fix low-value run with `aiBriefEnabled=true` does not gain new commands on replay.
