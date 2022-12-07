import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("연봉을 입력하세요.");
        long mymoney = sc.nextLong();

        int myLevel = 0;        // 과세 표중 항목의 등급화를 위해 변수 만듬.

        long myoutmoney = 0;    // 누적 공지계산액 토탈

        // 과세표준 금액 변수화111
        long[] level = new long[8];
        level[0] =         0;
        level[1] =  12000000;
        level[2] =  46000000;
        level[3] =  88000000;
        level[4] = 150000000;
        level[5] = 300000000;
        level[6] = 500000000;
        level[7] =1000000000;
        // 과세표준 세율 변수화
        long[] level_pr = new long[8];
        level_pr[0] = 6;
        level_pr[1] = 15;
        level_pr[2] = 24;
        level_pr[3] = 35;
        level_pr[4] = 38;
        level_pr[5] = 40;
        level_pr[6] = 42;
        level_pr[7] = 45;
        // 과세표준 구진공제 변수화
        long[] level_jin = new long[8];
        level_jin[0] =          0;
        level_jin[1] =    1080000;
        level_jin[2] =    5220000;
        level_jin[3] =   14900000;
        level_jin[4] =   19400000;
        level_jin[5] =   25700000;
        level_jin[6] =   35400000;
        level_jin[7] =   65400000;
        // 과세 표준 세율에 다른 세금 Log 목적
        long[] level_neg = new long[8];
        
        System.out.println("\n\n");

        // 세금 계산 //
        for (int i = 0; i < level.length-1; i++)
        {
            if(level[i] < mymoney){
                if((mymoney > level[i+1])){  // 다음 세율듬급보다 큰지 작은지를 확인해서 크면 등급변위 전액을 세금 계산
                    if(i > 0){
                        level_neg[i] = (level[i+1] - level[i]) * level_pr[i] / 100;
                    }else{
                        level_neg[i] = level[i+1] * level_pr[i] / 100;
                    }
                    myoutmoney += level_neg[i];
                }else // 다음 세율듬급보다 작으면 연봉 - 현세율듬급 하여 남은금액으로 세금 계산
                {
                    if(i > 0){
                        level_neg[i] = (mymoney - level[i]) * level_pr[i] / 100;
                    }else{
                        level_neg[i] = mymoney * level_pr[i] / 100;
                    }
                    myoutmoney += level_neg[i] ;
                    myLevel = i;
                    break;
                }
            } else { break; }
        }
        // 과세표준 마지막 부분만 따로 나누어서 프로그램.
        if(level[7] < mymoney){
            level_neg[7] = (mymoney - level[7]) * level_pr[7] / 100;
            myoutmoney += level_neg[7];
            myLevel = 7;
        }
        /////////  결과 값  /////////
        System.out.println("[ 결과 값 ]");
        for (int i = 0; i < myLevel+1; i++) {

            if(i != myLevel){
                System.out.println(String.format("%13d",(level[i+1] - level[i])) + " *" + String.format("%3d",level_pr[i]) + "% = " + String.format("%13d",level_neg[i]));
            } else if(i == myLevel){
                System.out.println(String.format("%13d",(mymoney - level[i])) + " *" + String.format("%3d",level_pr[i]) + "% = " + String.format("%13d",level_neg[i]));
            }
        }
        System.out.println();
        System.out.println("[ 세율에 의한세금 ]:      " + String.format("%13d",myoutmoney));
        System.out.println("[누진공재 계산에 의한 세금]:" + String.format("%13d",(myoutmoney - level_jin[myLevel])));
        System.out.println("[ 실금액 ] :" + (mymoney - myoutmoney + level_jin[myLevel]));
    }
}