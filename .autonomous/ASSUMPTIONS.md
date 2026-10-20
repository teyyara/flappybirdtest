# Assumptions

- Intended product is a single-player offline Flappy Bird-style game, not a commercial clone using original assets.
- Portrait-only interaction is appropriate for one-thumb arcade play.
- Difficulty and timing will be tuned from emulator evidence after the first play-through.
- Best score is stored in private app preferences and does not sync to a server.
- API 24 is the minimum supported platform. Compile against API 37 for current Compose dependencies; keep target API 36 until Android 17 target behavior is separately reviewed. Verify current Play requirements again if distribution becomes a goal.
