# ryanair

Notes:
- No AI tools were used in this project.
- I added (as we discussed) a validation that makes mandatory that departure and arrival must be the same day.
- Because of that, I added a validation to avoid flights that arrive the following day (at night), because it's the easiest way, keeping in mind that flights will be within one day. But validations would change in case of allowing multiple dates and the whole project would be different (we can discuss it if wanted).
- The architecture used is done keeping in mind that this is a small application. The approach would be different in a big one and we can discuss about the differences if wanted (for example, application of solid principles varies).
- The same thing applies to exception handling, and testing.
