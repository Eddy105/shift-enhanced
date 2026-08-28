# Changelog

## Unreleased

- Add session battery drain rate derived from capacity change and elapsed time
- Surface battery drain rate in the Power Center
- Add session-based estimated remaining battery runtime
- Avoid runtime estimates for charging, missing capacity, empty battery, insufficient samples, or invalid drain rates
- Cover battery analytics and runtime edge cases with unit tests

## 0.1.2

- Add live in-memory power session recording
- Surface session sample count, duration, and average current
- Keep session data local to the active app process
- Add recorder and duration-formatting tests

## 0.1.1

- Extend power telemetry with battery temperature and voltage
- Expose charging state in the dashboard
- Add safe handling for unavailable battery fields
- Expand telemetry model tests

## 0.1.0

- Initial Android application foundation
- Battery percentage and current telemetry
- Power telemetry model and tests
- Android CI with debug APK artifact
