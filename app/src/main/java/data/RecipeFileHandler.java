package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeFileHandler {
    private String filePath;

    public RecipeFileHandler() {
        filePath = "app/src/main/resources/recipes.txt";
    }

    public RecipeFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 設問1: 一覧表示機能
     * recipes.txtからレシピデータを読み込み、それをリスト形式で返します。 <br> 
     * IOExceptionが発生したときは<i>Error reading file: 例外のメッセージ</i>とコンソールに表示します。
     *
     * @return レシピデータ
     */
    //tryでrecipes.txtからデータを読み1行毎に配列に格納 格納した配列をreturn
    public ArrayList<String> readRecipes() {
        //recipes.txtからデータ呼び出し
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //変数の宣言
            ArrayList<String> data = new ArrayList<>();
            String line;

            //nullを呼ぶまで1行ずつ呼び出し
            while ((line = reader.readLine()) != null) {
                //呼び出したデータの格納
                data.add(line);
            }
            return data;

        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return null;
    }

    /**
     * 設問2: 新規登録機能
     * 新しいレシピをrecipes.txtに追加します。<br>
     * レシピ名と材料はカンマ区切りで1行としてファイルに書き込まれます。
     *
     * @param recipeName レシピ名
     * @param ingredients 材料名
     */
     // 全レシピを読んで配列に格納
     // 引数よりレシピ情報を受け取り配列に追記
     // \nで配列をすべてjoinし情報を保存
    public void addRecipe(String recipeName, String ingredients) {
        //recipes.txtからデータ呼び出し
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            //変数の宣言
            ArrayList<String> data = new ArrayList<>();
            String line;

            //nullを呼ぶまで1行ずつ呼び出し
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }

            //引数を加工・格納
            data.add(recipeName + "," + ingredients);

            //保存用に変数を加工
            String input = String.join("\n", data);

            //recipes.txtへデータ書き込み
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                //加工した変数を書き込み
                writer.write(input);
            } catch (IOException e) {
                System.out.println("Error reading file:" + e.getMessage());
            }

            //完了した旨を出力
            System.out.println("Recipe added successfully.");
            return;

        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return;
    }
}
