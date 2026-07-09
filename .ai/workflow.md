# Workflow

## Before changing code

1. Read relevant docs in `docs/`.
2. Identify feature ownership.
3. Check whether the change affects mobile, TV, Fire TV, backend, player, or ads.
4. Avoid guessing architecture decisions when canonical docs exist.

## During implementation

- Keep changes scoped.
- Update documentation when behavior changes.
- Do not mix unrelated feature work.
- Add tests where practical.
- Record known limitations.

## Before opening a PR

Use `.github/PULL_REQUEST_TEMPLATE.md`.

Confirm:

- No secrets are included.
- No build artifacts are included.
- No unlicensed video links are included.
- Player lifecycle is correct if player changed.
- Ad policy is respected if ads changed.
- TV/Fire TV focus behavior is considered if UI changed.

## Documentation rule

If a durable decision changes architecture, platform support, player flow, ad enforcement, auth, database, or streaming, update the relevant doc in `docs/`.
