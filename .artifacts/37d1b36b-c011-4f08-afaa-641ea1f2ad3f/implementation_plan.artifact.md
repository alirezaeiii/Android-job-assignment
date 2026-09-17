# Implementation Plan - Write Unit Tests for SectionRepository and SectionViewModel

This plan covers adding unit tests for the `SectionRepository` in the `:core:data` module and the `SectionViewModel` in the `:feature:feature-details` module.

## Proposed Changes

### Core Data Component

#### [NEW] [SectionRepositoryTest.kt](file:///Users/ali/AndroidStudioProjects/Housing/core/data/src/test/java/com/viaplay/test/data/repository/SectionRepositoryTest.kt)
- Create a test class for `SectionRepository`.
- Mock `BackendApi`, `SectionEntityDao`, and `Context`.
- Use `UnconfinedTestDispatcher` for coroutine testing.
- Test cases:
    - `getResult` with empty database: should fetch from network and save to database.
    - `getResult` with data in database: should emit database data and (optionally) refresh.
    - `getResult` on failure: should emit error state.

### Feature Details Component

#### [NEW] [SectionViewModelTest.kt](file:///Users/ali/AndroidStudioProjects/Housing/feature/feature-details/src/test/java/com/viaplay/test/feature/details/SectionViewModelTest.kt)
- Create a test class for `SectionViewModel`.
- Mock `BaseRepository<Section, String, String>` and `SavedStateHandle`.
- Test cases:
    - Initial state: should be loading.
    - `onSuccess`: should update state with the `Section` data.
- Ensure the `SavedStateHandle` contains a `Link` object to trigger the repository call with correct parameters.

## Verification Plan

### Automated Tests
- Run `:core:data:testDebugUnitTest`
- Run `:feature:feature-details:testDebugUnitTest`
