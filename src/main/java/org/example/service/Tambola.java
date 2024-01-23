package org.example.service;

import java.util.*;

import static org.example.utils.constant.COL;
import static org.example.utils.constant.ROW;

public class Tambola {

    List<Integer> indices;
    List<int[][]> tickets;
    int N;
    public Tambola(int n){
        tickets = new ArrayList<>();
        indices = new ArrayList<>();
        for(int i=0;i<COL;i++){
            indices.add(i);
        }
        N = n;
    }

    public void generateTickets(){
        int[] rowCounter = {0,0,0}; //5
        int[] colCounter = {0,0,0,0,0,0,0,0,0};
        int[][] list = new int[ROW][COL];
        randomTicketGenerator(list,0,0,rowCounter,colCounter);
        for(int[][] ticket:tickets) {
            populateTicket(ticket);
        }
        for(int[][] ticket:tickets){
            printTicket(ticket);
        }
    }

    private void populateTicket(int[][] ticket){
        for(int j=0;j<COL;j++){
            int start = j*10, end = j*10 + 9;
            if(j==0){
                start = 1;
            }
            //get 9 number in start to end range
            List<Integer> rangeList = new ArrayList<>();
            for(int i=start;i<end;i++){
                rangeList.add(i);
            }

            //get 3 random incremental indices
            List<Integer> randomIndices = getRandomIndices();
            int randomIndicesCounter = 0;

            for(int i=0;i<ROW;i++){
                if(ticket[i][j] == 0){
                    continue;
                }
                int randomIndex = randomIndices.get(randomIndicesCounter++);
                ticket[i][j]=rangeList.get(randomIndex);
            }
        }
    }

    private List<Integer> getRandomIndices(){
        Collections.shuffle(indices);
        List<Integer> list =  indices.subList(0,3);
        Collections.sort(list);
        return list;
    }

    private void randomTicketGenerator(int[][] ticket,int ro,int col,int[] rowCounter,int[] colCounter){
        if(N==0) return;
        if(ro==ROW){
            for(int i=0;i<3;i++){
                if(rowCounter[i]!=5){
                    return;
                }
            }
            for(int i=0;i<9;i++){
                if(colCounter[i]==0){
                    return;
                }

            }
            N--;
            int[][] newArray = new int[ROW][COL];
            for (int i = 0; i < ROW; i++) {
                newArray[i] = Arrays.copyOf(ticket[i], COL);
            }
            tickets.add(newArray);
            return;
        }
        if(col==COL){
            randomTicketGenerator(ticket,++ro,0,rowCounter,colCounter);
            return;
        }
        randomTicketGenerator(ticket,ro,col+1,rowCounter,colCounter);
        if(rowCounter[ro] < 5){
            ticket[ro][col]=1;
            rowCounter[ro]++;
            colCounter[col]++;
            randomTicketGenerator(ticket,ro,col+1,rowCounter,colCounter);
            if(N == 0) return;
            ticket[ro][col]=0;
            rowCounter[ro]--;
            colCounter[col]--;
        }

    }

    private void printTicket(int[][] ticket){
        for(int[] row:ticket){
            for(int col:row){
                System.out.print(col + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }


    private List<List<Integer>> createEmptyTicket(){
        List<List<Integer>> ticket = new ArrayList<>(ROW);
        for(int i=0;i<ROW;i++){
            ticket.add(new ArrayList<>(COL));
            for(int j=0;j<COL;j++){
                if(j<5)
                    ticket.get(i).add(1);
                else
                    ticket.get(i).add(0);
            }
            Collections.shuffle(ticket.get(i));
        }
        return ticket;
    }


}
