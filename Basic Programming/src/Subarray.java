import java.util.Scanner;
 
public class Main {
	static Scanner sc = new Scanner(System.in);
 
	int sol(int zeroCount, int oneCount, int setOfOne,int firstEle) {
		
		if (zeroCount < (oneCount - 1) && zeroCount != 0 && oneCount != 0) {
			return -1;
		}
		if ((zeroCount >= oneCount - 1 || zeroCount == oneCount) && setOfOne != 0) {
			if (firstEle==0) {
				return (setOfOne+1);
			}
			else {
				return setOfOne;
			}
		}
		return 0;
	}
 
	void meth1(int testCase) {
		int i = 0, noEle = 0, inputEle, setOfOne, zeroCount, oneCount,k;
		int[] resultArr = new int[testCase];
		while (i < testCase) {
			k=-1;
			zeroCount = 0;
			oneCount = 0;
			setOfOne = 0;
			noEle = sc.nextInt();
			int[] eleArr = new int[noEle];
			for (int j = 0; j < noEle; j++) {
				inputEle = sc.nextInt();
 
				if (j != 0) {
					if (j !=eleArr.length) {
						if (inputEle == 1 && eleArr[j - 1] == 1) {
							if (k!=j-1) {
								setOfOne++;
								k=j;
							}
						}
					}
				}
				if (inputEle == 0) {
					zeroCount++;
				} else {
					oneCount++;
				}
				eleArr[j] = inputEle;
			}
			resultArr[i] = sol(zeroCount, oneCount, setOfOne,eleArr[0]);
			i++;
		}
		for (int j : resultArr) {
			System.out.println(j);
		}
	}
 
	public static void main(String[] args) {
		new Main().meth1(sc.nextInt());
	}
}
Language: Java 8
