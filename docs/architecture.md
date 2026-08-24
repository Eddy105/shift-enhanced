# Architecture

SHIFT Enhanced is intentionally built as a native Android application first.

## v0.1

The Power Foundation reads battery telemetry through Android platform APIs and presents it locally. No telemetry is transmitted off-device.

## Planned boundaries

- `power`: battery state, sessions and analytics
- `diagnostics`: hardware and system self-tests
- `privacy`: permissions and network controls
- `actions`: configurable hardware/action shortcuts
- `developer`: desktop and developer tooling

The application should remain useful without privileged/root access. Features requiring elevated permissions will be isolated behind explicit capability checks.
