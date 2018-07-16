package demo.list.com.map;

//public class Demo {
//    public static void main(String[] args) {
//        public String replaceSpace(StringBuffer str) {
//            StringBuffer sb = new StringBuffer();
//            for(int i=0;i<str.toString.length;i++){
//                if(String.valueOf(str.charAt(i)).endsWith(" ")){
//                    sb.append("%20");
//                }else{
//                    sb.append(String.valueOf(str.charAt(i)));
//                }
//            }
//            return sb.toString;
//        }
////        Scanner scanner = new Scanner(System.in);
////
////        System.out.println("请输入金币个数：");
////        int number = scanner.nextInt();
////
////        System.out.println("请输入每个位置所消耗金币数量：");
////        scanner.nextLine();
////        String str = scanner.nextLine();
//
////        if (number == 0) {
////            System.out.print("-1");
////        } else {
////            String[] num = str.split(" ");
////            int min = 105;
////            int secondMin = 105;
////            int index = -1;
////            int secondIndex = -1;
////            for (int i = 0; i < num.length; i++) {
////                if (Integer.parseInt(num[i]) <= min) {
////                    min = Integer.parseInt(num[i]);
////                    index = i;
////                }
////            }
////
////            for (int i = 0; i < num.length; i++) {
////                if (Integer.parseInt(num[i]) <= secondMin && Integer.parseInt(num[i]) != min) {
////                    secondMin = Integer.parseInt(num[i]);
////                    secondIndex = i;
////                }
////            }
////
////            if (secondIndex!=-1) {
////                int s = (number % Integer.parseInt(num[secondIndex])) / Integer.parseInt(num[index]);
////                if (s != 0) {
////                    if (min == 1) {
////                        StringBuffer sb = new StringBuffer();
////                        for (int i = 0; i < number; i++) {
////                            sb.append("" + (index + 1));
////                        }
////                        System.out.print(sb.toString());
////                    } else {
////                        if (index > secondIndex) {
////                            int z = number / Integer.parseInt(num[index]);
////
////                            StringBuffer sb = new StringBuffer();
////                            for (int i = 0; i < z; i++) {
////                                sb.append("" + (index + 1));
////                            }
////                            System.out.print(sb.toString());
////                        } else {
////                            int o = number / Integer.parseInt(num[secondIndex]);
////
////                            StringBuffer sb = new StringBuffer();
////                            for (int i = 0; i < o; i++) {
////                                sb.append("" + (secondIndex + 1));
////                            }
////                            if (s != 0) {
////                                sb.append("" + (index + 1));
////                            }
////                            System.out.print(sb.toString());
////                        }
////                    }
////                } else {
////                    int z = number / Integer.parseInt(num[index]);
////
////                    StringBuffer sb = new StringBuffer();
////                    for (int i = 0; i < z; i++) {
////                        sb.append("" + (index + 1));
////                    }
////                    System.out.print(sb.toString());
////                }
////            }else {
////                if (number>=min){
////                    int z = number / Integer.parseInt(num[index]);
////
////                    StringBuffer sb = new StringBuffer();
////                    for (int i = 0; i < z; i++) {
////                        sb.append("" + (index + 1));
////                    }
////                    System.out.print(sb.toString());
////                }else {
////                    System.out.print("-1");
////                }
////            }
////        }
//    }
//}
public class Demo {

    public static void main(String[] args){
        StringBuffer sb = new StringBuffer("aa bb cc");
        replaceSpace(sb);
    }
    public static String replaceSpace(StringBuffer str) {
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<str.toString().length();i++){
            if(String.valueOf(str.charAt(i)).endsWith(" ")){
                sb.append("%20");
            }else{
                sb.append(String.valueOf(str.charAt(i)));
            }
        }
        return sb.toString();
    }
}