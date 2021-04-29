package darth.bartenderbot.utils.FS;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FileUtils {
    public void fsCheck() {
        //Sets up the bots fs
        File fileEmotes = new File("emotes");
        File fileDrinks = new File("drinks");
        //Add files to list
        List<File> fsConfig = new ArrayList<>();
        fsConfig.add(fileEmotes);
        fsConfig.add(fileDrinks);
        //For loop files checking their existence
        for (int i = 0; fsConfig.size() > i; i++) {
            if (!fsConfig.get(i).exists()) {
                boolean fsCreated = fsConfig.get(i).mkdir();
                if (fsCreated) {
                    System.out.println(fsConfig.get(i).getName() + " folder was missing and was created automatically");
                } else {
                    System.out.println(fsConfig.get(i).getName() + " folder was missing and failed to create automatically, Check your install folder!");
                }
            }
        }
    }

    public List<String> FileScan(File[] files) {
        List<String> FileList = new ArrayList<>();
        for (File Images : files) {
            File catfile = new File(Images.getPath());
            FileList.add(catfile.getName());
        }
        return FileList;
    }

    public File GetRandomFile(File directory) {
        File[] file = directory.listFiles();
        Random rand = new Random();
        try {
            int randnum = rand.nextInt(file.length);
            System.out.println(randnum);
            System.out.println(file.length);
            File folder = file[randnum];

            return folder;
        } catch (NullPointerException e) {

        }
        return null;
    }

    public File GetRandomImage(File folder) {
        File[] category = folder.listFiles();
        Random rand = new Random();
        File image = category[rand.nextInt(category.length)];
        return image;
    }

    public File GetDrinkByString(String string) {
        File file = new File("drinks/" + string);
        return file;
    }

    public File GetImageByString(File category, String filename) {
        File image = new File(category + "/" + filename);
        return image;
    }

    public File GetEmoteByString(String string) {
        File file = new File("emotes/" + string);
        return file;
    }

    public File[] GetFilesByString(File folder, String string) {
        File file = new File(folder + "/" + string);
        File[] files = file.listFiles();
        return files;
    }

    public boolean CategoryRename(String oldcat, String newcat) {
        File oldcategory = new File("drinks/" + oldcat);
        File category = new File("drinks/" + newcat);
        boolean bool = oldcategory.renameTo(category);
        return bool;
    }

    public boolean CategoryRemove(String category) {
        File file = new File("drinks/" + category);
        String[] files = file.list();
        for (String s : Objects.requireNonNull(files)) {
            File catfile = new File(file.getPath(), s);
            catfile.delete();
        }
        boolean bool = file.delete();
        return bool;
    }

    public boolean EmoteRename(String oldcat, String newcat) {
        File oldcategory = new File("emotes/" + oldcat);
        File category = new File("emotes/" + newcat);
        boolean bool = oldcategory.renameTo(category);
        return bool;
    }

    public boolean EmoteRemove(String category) {
        File file = new File("emotes/" + category);
        String[] files = file.list();
        for (String s : Objects.requireNonNull(files)) {
            File catfile = new File(file.getPath(), s);
            catfile.delete();
        }
        boolean bool = file.delete();
        return bool;
    }
}