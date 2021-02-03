package ir.ahooee.main;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class MyMainClass {

    final private static String[] FA_INTS_ONE = {"صفر","یک","دو","سه","چهار","پنج","شش","هفت","هشت","نه"};

    final private static String[] FA_INTS_TWO_0 = {"ده","یازده","دوازده","سیزده","چهارده","پانزده","شانزده","هفده","هجده","نوزده"};

    final private static String[] FA_INTS_TWO_1 = {"ده","بیست","سی","چهل","پنجاه","شصت","هفتاد","هشتاد","نود"};

    final private static String[] FA_INTS_THREE = {"صد","دویست","سیصد","چهارصد","پانصد","ششصد","هفتصد","هشتصد","نهصد"};

    final private static String[] FA_INTS_FOUR = {"ده","صد","هزار","میلیون","میلیارد","تریلیون","تریلیارد"};

    final private static String[] FA_FLOAT_SUFFIX = {"م","یوم"};

    final private static String SPACE = " ";

    final private static String PERSIAN_AND = SPACE + "و" + SPACE;

    final private static String POINT = SPACE + "ممیز" + SPACE;





    public static void main(String[] strs){



        bigDecimalToPersianText();






    }

    private static void bigDecimalToPersianText(){



        while(true) {

            System.out.print("Enter the NUMBER:");

            Scanner sc = new Scanner(System.in);

            String inputBigDeciaml = sc.nextLine();

            try {
                //initiate bigDecimal with input String
                BigDecimal bigDecimal = new BigDecimal(inputBigDeciaml.split("\\.")[0]);
                bigDecimal = new BigDecimal(bigDecimal.toPlainString());

                BigDecimal floatPointDecimal = new BigDecimal(0);

                System.out.println(bigDecimal);
                int floatPointLength = 0;

                //check if the input BigDecimal has float point part
                if(inputBigDeciaml.contains(".")) {
                    String float_Part_BigDecimal = inputBigDeciaml.split("\\.")[1];
                    floatPointDecimal = new BigDecimal(float_Part_BigDecimal);
                    floatPointLength = float_Part_BigDecimal.length();

                }

                System.out.println("***********************");
                System.out.println("THE OUTPUT:");


                if (bigDecimal.compareTo(new BigDecimal(0)) == 0)
                    System.out.println(FA_INTS_ONE[0]);
                else if (bigDecimal.compareTo(new BigDecimal(0)) < 0)
                    System.out.println("منفی " + integerPart(bigDecimal.multiply(new BigDecimal(-1)))+floatPart(floatPointDecimal,floatPointLength));
                else
                    System.out.println(integerPart(bigDecimal)+floatPart(floatPointDecimal,floatPointLength));

                System.out.println("***********************");


            }catch(NumberFormatException numberFormatException){

                System.out.println(numberFormatException.getMessage()+"\n"+"wrong format!!!");

            }
        }





    }

    private static String zreoTotousand(int integerIn) {


        String FA_OUT = "";

        boolean type_VA = false;

        if(integerIn>=100 && integerIn<=999){


            int key =  integerIn/100*100;

            integerIn -=key;

            FA_OUT = FA_INTS_THREE[key/100-1] ;

            type_VA = true;


        }
        if(integerIn>=20 && integerIn<=99 ){

            int key =  integerIn/10*10;

            FA_OUT =type_VA? FA_OUT+ PERSIAN_AND + FA_INTS_TWO_1[key/10-1]: FA_INTS_TWO_1[key/10-1] ;

            integerIn -= key;

            type_VA = true;


        }
        if(integerIn>=10 && integerIn<=19){


            int key =  integerIn-10;

            FA_OUT = type_VA?FA_OUT+ PERSIAN_AND + FA_INTS_TWO_0[key]: FA_INTS_TWO_0[key] ;




        }else if(integerIn>0 && integerIn<=9){

            int key = integerIn;

            FA_OUT =type_VA?FA_OUT+ PERSIAN_AND + FA_INTS_ONE[key]: FA_INTS_ONE[key];






        }






        return  FA_OUT;
    }


    private static String integerPart(BigDecimal bigDecimalIn ){




        BigDecimal tempInt;

        String persianOut = "";

        boolean typePersianAnd = false ;





        for(int i=15,j=4; i >= 0; i-=3,j--){

            if(bigDecimalIn.compareTo(BigDecimal.valueOf(Math.pow(10, i)))>=0 && bigDecimalIn.compareTo(BigDecimal.valueOf(Math.pow(10, i+3)))<0){


                tempInt = bigDecimalIn.divide(BigDecimal.valueOf(Math.pow(10, i)),RoundingMode.FLOOR);


                persianOut += typePersianAnd? PERSIAN_AND + zreoTotousand(tempInt.intValue())+(j>=0?SPACE+ FA_INTS_FOUR[j+2]:""): zreoTotousand(tempInt.intValue())+(j>=0?SPACE+ FA_INTS_FOUR[j+2]:"");



                bigDecimalIn=bigDecimalIn.subtract(tempInt.multiply(new BigDecimal(Math.pow(10,i))));



                typePersianAnd = true;



            }
        }


        return  persianOut;
    }


    private static String floatPart(BigDecimal bigDecimalIn ,int floatLength){

        String persianOut = POINT;
        if(bigDecimalIn.compareTo(new BigDecimal(0))==0)
            return "";




            persianOut += integerPart(bigDecimalIn) + SPACE + (floatLength%3>0? FA_INTS_FOUR[(floatLength%3)-1]:"")+(floatLength>3?SPACE:"")+
                    (floatLength>=3? FA_INTS_FOUR[(floatLength/3)+1]:"")+(floatLength<6? FA_FLOAT_SUFFIX[0]: FA_FLOAT_SUFFIX[1]);





        return persianOut;
    }


}
