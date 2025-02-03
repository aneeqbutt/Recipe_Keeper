package com.example.myapplication;



    public class chickken {
        private int id;
        private String recipeName;
        private String recipeDescription;

        // Constructor
        public chickken(int id, String recipeName, String recipeDescription) {
            this.id = id;
            this.recipeName = recipeName;
            this.recipeDescription = recipeDescription;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getRecipeName() {
            return recipeName;
        }

        public String getRecipeDescription() {
            return recipeDescription;
        }
    }
