public class Main {

    public static void main(String[] args) throws Exception {
        for (String i:args){
            System.out.println(i);
        }
//        try {
//            purgeDirectory(new File("C:\\Users\\user\\IdeaProjects\\softdev-2023-spring-task-2\\split\\build\\jar"));
//        }
//        catch (NullPointerException e){
//        }
        Split split=new Split();
        split.getArgs(args);
        split.split();

    }
}
