# Named Entity Annotation Guide

`VEH` does not appear in my dataset.
`ORG` does not appear in my dataset.

## Labels

### Person (PER)

New specific examples:
- Harding
- the settlers
- the engineer
- Cyrus Harding
- Neb


### Facility (FAC)

New specific examples:
- the Chimneys
- dwelling
- the larder
- the colony
- the dockyards


### Geo-political entities (GPE)

New specific examples:
- Brooklyn
- Lake Grant
- South America


### Location (LOC)

New specific examples:
- the lake's bank
- the shore
- this lake
- the island


### Vehicle (VEH)

New specific examples:

NONE

### Organisation (ORG)

New specific examples:

NONE

## Rare cases

Add at least two rare / edge / special cases and how they should be handled.
- the forge: I label it as `FAC` because it was human made, and human can use it to produce. However, this does not clearly state in the `FAC` description. That's why I think it is hard to decide.

- the Mercy: I labeled it as `LOC` because, based on the paragraph, I believe it refers to a location. However, I am not sure whether it qualifies as a `GPE`, since I could not find this place on Earth and am uncertain whether it has clearly defined boundaries.