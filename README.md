# Dogs App (Assignment)

## Simple Viral Games

### Android Assignment

#### Problem Statement
Create an app that generates random images of dogs that are saved for viewing later.
There will be three screens in this app:

● Home Screen: Buttons that lead to the other two screens.

● Generate Dogs Screen:

○ Has a “Generate!” button that sends a request to a public dog images API at
https://dog.ceo/api/breeds/image/random

○ When the generate button is hit and the image data is good, it is displayed,
and stored in a Cache.

○ The Cache:

■ An LRU cache: Holds the 20 most recent image data generated from
requests to the above API. Don’t worry about time/space efficien
here.

■ Make sure this persists from app session to app session.

● My Recently Generated Dogs Screen:

○ Has a scrollable gallery of images that is created from the data held by the
cache.
○ Has a “Clear Dogs” button that clears out the cache, and the Gallery.

**NOTE**: The RGB values for the given buttons are R:66, G:134, B: 244.
Please try to adhere as closely as possible to the designs in the accompanying demo video.
Video: https://bit.ly/3gtFCbV

It is preferable to implement this using Jetpack Compose.

#### Guidelines

Please go through the below guidelines to check-in your codebase.

#### Codebase

Check-in the codebase to any version control and make it public. Share the link to the
repository.

## Solution:

### Architecture Pattern

Clean Architecture

```
(Structure)

com.svg.dogsapp
\- common (utilities common to all layers)

\- data (deals with raw data, either network or local) 
\|--> di
\|--> local
\|--> model
\|--> network
\|--> repository

\- domain (deals with processed data)
\|--> di
\|--> local
\|--> model
\|--> repository
\|--> use_cases

\- presentation (deals with presenting data to user)

\- ui

\- utils 
```

### My Approach:

1. Created layered architecture with specific responsibilities in each layer.
2. Used Hilt for dependency injection.
3. Used Retrofit for network calls.
4. Used Room for local database.
5. Used Coil for image loading.
6. Used Jetpack Compose for UI.

### Tests and Profiles:

1. Tested on emulated and physical devices.
2. Tested on API level 22 (emulator) and API level 33 (physical device).
3. Profiled for System Trace, Heap and Memory Consumption

### Known Warnings:

```
21:55:44.850  4528-4590  CursorWindow            com.svg.dogsapp                      W  Window is full: requested allocation 656786 bytes, free space 362734 bytes, window size 2097152 bytes

(This is because of the 2MB CursorWindow Limit in SQLite database. 
As per the problem statement, I need not worry about time/space efficieny here.
However, I have taken care of the 20 most recent image data generated from requests to the given API.
I have compressed the data to have 80kB or less for each image. This way, I can store 80KB * 20 = 1600kB, 
which is less than 2000kB or 2MB CursorWindow Limit. Therefore, the expected output is not affected. 
i.e. the LRU Cache works as expected in SQLite database.)

```