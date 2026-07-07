# Music Recommendation Dashboard
# R Shiny application with ggplot2 visualizations

library(shiny)
library(ggplot2)
library(dplyr)

# Load datasets
songs <- read.csv("songs.csv", stringsAsFactors = FALSE)
artists <- read.csv("artists.csv", stringsAsFactors = FALSE)

# Merge songs with artist info
song_data <- songs %>%
  left_join(artists, by = "artist") %>%
  select(song_id, title, artist, genre = genre.x, release_year, rating, plays, country)

# UI
ui <- fluidPage(
  theme = bslib::bs_theme(bootswatch = "flatly"),
  
  titlePanel("Music Recommendation Dashboard"),
  
  sidebarLayout(
    sidebarPanel(
      h4("Filters"),
      selectInput("genreFilter", "Genre",
                  choices = c("All", unique(song_data$genre)),
                  selected = "All"),
      selectInput("artistFilter", "Artist",
                  choices = c("All", unique(song_data$artist)),
                  selected = "All"),
      sliderInput("yearRange", "Release Year Range",
                  min = min(song_data$release_year),
                  max = max(song_data$release_year),
                  value = c(min, max)),
      hr(),
      h5("Recommendation Logic"),
      p("Songs are filtered by your selections and sorted by rating to show top recommendations.")
    ),
    
    mainPanel(
      tabsetPanel(
        tabPanel("Recommendations",
                 h4("Recommended Songs"),
                 tableOutput("recTable"),
                 hr(),
                 h4("Top Rated Song"),
                 textOutput("topSong")
        ),
        tabPanel("Visualizations",
                 h4("Genre Distribution"),
                 plotOutput("genrePlot", height = "300px"),
                 h4("Rating vs Release Year"),
                 plotOutput("ratingPlot", height = "300px")
        ),
        tabPanel("About",
                 h4("About This Dashboard"),
                 p("This Music Recommendation System uses simple filter-based logic:"),
                 tags$ul(
                   tags$li("Select genre and/or artist to narrow down songs"),
                   tags$li("Adjust year range for era preference"),
                   tags$li("Recommendations are sorted by rating (highest first)"),
                   tags$li("Visualizations show genre distribution and rating trends")
                 ),
                 p("Built with R Shiny and ggplot2")
        )
      )
    )
  )
)

# Server
server <- function(input, output, session) {
  
  # Reactive filtered data
  filtered_data <- reactive({
    data <- song_data
    
    if (input$genreFilter != "All") {
      data <- data %>% filter(genre == input$genreFilter)
    }
    if (input$artistFilter != "All") {
      data <- data %>% filter(artist == input$artistFilter)
    }
    data <- data %>% filter(release_year >= input$yearRange[1],
                            release_year <= input$yearRange[2])
    data <- data %>% arrange(desc(rating))
    data
  })
  
  # Recommendations table
  output$recTable <- renderTable({
    df <- filtered_data()
    if (nrow(df) == 0) {
      return(data.frame(Message = "No songs match your criteria."))
    }
    df %>% select(title, artist, genre, release_year, rating, plays)
  })
  
  # Top song text
  output$topSong <- renderText({
    df <- filtered_data()
    if (nrow(df) == 0) return("No songs found")
    top <- df %>% slice_max(rating, n = 1)
    paste0("Top Recommendation: '", top$title[1], "' by ", top$artist[1],
           " (Rating: ", top$rating[1], ")")
  })
  
  # Genre distribution bar chart
  output$genrePlot <- renderPlot({
    df <- filtered_data()
    if (nrow(df) == 0) return(NULL)
    
    ggplot(df, aes(x = reorder(genre, -table(genre)[genre]))) +
      geom_bar(fill = "#4a90d9", color = "white", width = 0.6) +
      labs(title = "Songs by Genre", x = "Genre", y = "Count") +
      theme_minimal() +
      theme(plot.title = element_text(hjust = 0.5, face = "bold"))
  })
  
  # Rating vs Release Year scatter plot
  output$ratingPlot <- renderPlot({
    df <- filtered_data()
    if (nrow(df) == 0) return(NULL)
    
    ggplot(df, aes(x = release_year, y = rating, color = genre)) +
      geom_point(size = 4, alpha = 0.7) +
      geom_smooth(method = "lm", se = FALSE, color = "gray50", linetype = "dashed") +
      labs(title = "Rating by Release Year",
           x = "Release Year", y = "Rating", color = "Genre") +
      theme_minimal() +
      theme(plot.title = element_text(hjust = 0.5, face = "bold"))
  })
}

# Run the app
shinyApp(ui = ui, server = server)
