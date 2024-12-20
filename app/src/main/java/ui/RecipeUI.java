package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

import data.RecipeFileHandler;

public class RecipeUI {
    private BufferedReader reader;
    private RecipeFileHandler fileHandler;

    public RecipeUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        fileHandler = new RecipeFileHandler();
    }

    public RecipeUI(BufferedReader reader, RecipeFileHandler fileHandler) {
        this.reader = reader;
        this.fileHandler = fileHandler;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        // 設問1: 一覧表示機能
                        displayRecipes();
                        break;
                    case "2":
                        // 設問2: 新規登録機能
                        addNewRecipe();
                        break;
                    case "3":
                        // 設問3: 検索機能
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exit the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /**
     * 設問1: 一覧表示機能
     * RecipeFileHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     */
    //readRecipesから受け取った引数を1行ごとに呼んで成形 コンソールに出力
    private void displayRecipes() {
        //----------変数の宣言----------
        ArrayList<String> data = new ArrayList<>();
        data = fileHandler.readRecipes();

        //----------空確認----------
        if(data.isEmpty()){
            System.out.println("No recipes available.");
            return;
        }

        //----------成形・出力処理----------
        for(String i : data){
            //要素毎に分離
            String[] parts = i.split(",");

            //出力
            System.out.println("-----------------------------------");
            System.out.println("Recipe Name: " + parts[0]);     //名前
            System.out.print("Main Ingredients: ");           //材料ヘッダ
            //要素毎に出力
            for(int j = 0; j < parts.length; j++){
                if(j != 0)System.out.print(parts[j]);
                if(j != 0 && j != parts.length - 1)System.out.print(", ");
            }
            System.out.print("\n");
        }
        //最後の行用
        System.out.println("-----------------------------------");
    }

    /**
     * 設問2: 新規登録機能
     * ユーザーからレシピ名と主な材料を入力させ、RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    // ユーザーにレシピ名と材料を入力させ変数に格納 addRecipeを入力された変数を引数に呼び出す
    private void addNewRecipe() throws IOException {
        //レシピ名入力要求
        System.out.print("Enter recipe name: ");
        String recipeName = reader.readLine();

        //材料入力要求
        System.out.print("Enter main ingredients (comma separated): ");
        String ingredients = reader.readLine();

        //レシピ追加呼び出し
        fileHandler.addRecipe(recipeName, ingredients);
    }

    /**
     * 設問3: 検索機能
     * ユーザーから検索クエリを入力させ、そのクエリに基づいてレシピを検索し、一致するレシピをコンソールに表示します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    //ユーザーにクエリを入力させ要素毎に分離 readRecipesを呼び出しクエリと比較 一致した場合内容を出力
    private void searchRecipe() throws IOException {
        //クエリ入力受付
        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String[] query = reader.readLine().split("&");

        ArrayList<String> datas = new ArrayList<>();
        datas = fileHandler.readRecipes();

        boolean noFlg = true;
        System.out.println("Search Results:");

        //格納データの要素毎にfor
        for(String data : datas){
            //格納データ行要素の分離
            String[] parts = data.split(",");

            //検索結果の一時保持
            String[] queryResult = query;

            //クエリの要素毎にfor
            for(int i = 0; i < queryResult.length; i++){
                //クエリ要素の内容分離
                String[] queryType = queryResult[i].split("=");
                //クエリの内容毎に分岐
                switch (queryType[0]) {
                    case "name":
                        //nameの場合格納データ要素の最初のみif
                        if(parts[0].contains(queryType[1])) queryResult[i] = "true";
                        break;
                    case "ingredient":
                        //ingredientの場合最初以外とif
                        for(int o = 0; o < parts.length; o++){
                            if(o != 0){
                                if(parts[o].contains(queryType[1])) queryResult[i] = "true";
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            //queryResultの全要素が"true"だった場合要素を出力
            boolean flg = true;
            for (int i = 0; i <= queryResult.length - 1; i++) {
                if (!queryResult[i].equals("true")) { //"true"以外の場合falseを代入
                    flg = false;
                }
            }
            if(flg)System.out.println(data);
            
            //要素毎に分けて検索できてないので破棄
            // if(i.contains(name[1]) && i.contains(ingredients[1])){
            //     System.out.println(i);
            //     flg = false;
            // }
        }
        if(noFlg)System.out.println("No recipes found matching the criteria.");

    }

}

