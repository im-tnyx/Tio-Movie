## Summary

-

## Type Of Change

- [ ] Documentation
- [ ] Android mobile app
- [ ] Android TV / Google TV
- [ ] Fire TV / Fire TV Stick
- [ ] Navigation
- [ ] Architecture
- [ ] Supabase / data
- [ ] Video streaming
- [ ] Player
- [ ] Ads / IMA
- [ ] UI
- [ ] Build or tooling

## Architecture And Documentation

- [ ] Relevant documentation updated.
- [ ] ADR updated or added if a durable architecture decision changed.
- [ ] Architecture changelog updated if module boundaries, navigation, data flow, player flow, ad flow, or engineering practice changed.
- [ ] Android progress doc updated if implementation status changed.
- [ ] Platform support doc updated if mobile, TV, or Fire TV behavior changed.
- [ ] No documentation change needed.

## Safety / Truth Boundary

- [ ] No secrets, service-role keys, keystores, private keys, video provider keys, ad account secrets, or local `.env` values are included.
- [ ] No generated/cache/build artifacts are included.
- [ ] No Supabase migration, RLS, RPC, or live schema change is implied unless it is actually included.
- [ ] No unlicensed video source, scraping logic, or unauthorized streaming link is included.
- [ ] Hardcoded demo data is temporary scaffolding or clearly replaced by repository-backed data.
- [ ] Feature ownership follows the canonical docs.

## Definition Of Done

- [ ] Requirements implemented.
- [ ] Module ownership respected.
- [ ] Screens remain dumb UI.
- [ ] ViewModel/domain/repository boundaries respected.
- [ ] Navigation remains type-safe.
- [ ] Player lifecycle is handled correctly where applicable.
- [ ] Ad flow follows documented IMA/backend policy where applicable.
- [ ] Android TV / Fire TV focus behavior considered where applicable.
- [ ] Loading, empty, and error states considered where applicable.
- [ ] Unit tests pass or are consciously scoped out.
- [ ] Compile/build checks pass or the reason is documented.
- [ ] No new compiler warnings introduced.
- [ ] UI matches approved design/product direction, if applicable.
- [ ] Accessibility reviewed, if applicable.

## Validation

List commands actually run:

-

## Notes

Add implementation notes, intentional deviations, or non-applicable checklist explanations.
