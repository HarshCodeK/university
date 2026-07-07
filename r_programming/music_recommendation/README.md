# Music Recommendation Dashboard

An R Shiny dashboard that recommends songs based on genre and artist filters.

## Data

- **songs.csv**: 20 songs with title, artist, genre, release_year, rating (1-5), plays
- **artists.csv**: 17 artists with genre, country, debut year
- **genres.csv**: 6 genre categories

## Features

- Sidebar filters: Genre, Artist, Release Year Range
- Recommendations table sorted by rating
- Top song highlight
- Genre distribution bar chart (ggplot2)
- Rating vs Release Year scatter plot
- About tab explaining recommendation logic

## How to Run

```bash
# Install required packages in R/RStudio
install.packages(c("shiny", "ggplot2", "dplyr", "bslib"))

# Run the app
Rscript app.R
```

Or open `app.R` in RStudio and click "Run App".

## Recommendation Logic

Filters by selected genre/artist/year, then sorts by highest rating.

## Project Structure

```
music_recommendation/
├── app.R          # Shiny application
├── songs.csv      # Song dataset
├── artists.csv    # Artist dataset
├── genres.csv     # Genre dataset
└── README.md
```
