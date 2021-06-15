package pl.piotrkorys.chess;

import java.util.ArrayList;

public class CheckmateController {
    public static boolean isCheckMate(Board chessboard, String color){
        Field[] fields = chessboard.fields;
        chessboard.fixPositions();
        for (int i = 0; i < fields.length; i++){
            if(fields[i].getPieceAtField() != null){
                if(fields[i].getPieceAtField().getPieceColor().equals(color)){
                    Piece pieceOnField = fields[i].getPieceAtField();
                    switch (pieceOnField.getPieceType()){
                        case "pawn":{
                            if(!isPawnInMate((Pawn) pieceOnField, chessboard, color))
                                return false;
                            break;
                        }
                        case "knight":{
                            if(!isKnightInMate((Knight) pieceOnField, chessboard, color))
                                return false;
                            break;
                        }
                        case "bishop":{
                            if(!isBishopInMate((Bishop) pieceOnField, chessboard, color))
                                return false;
                            break;
                        }
                        case "rook":{
                            if(!isRookInMate((Rook) pieceOnField, chessboard, color))
                                return false;
                            break;
                        }
                        case "queen":{
                            if(!isQueenInMate((Queen) pieceOnField, chessboard, color))
                                return false;
                            break;
                        }
                        case "King":{
                            if(!isKingInMate((King) pieceOnField, chessboard, color))
                                return false;
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static boolean isPawnInMate(Pawn pawn,Board chessboard, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[][] moves = new int[][]{{0,1}, {0,2}, {-1,1}, {1,1}};
        for (int i = 0; i < moves.length; i++){
            Board temp = new Board(chessboard); temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(pawn.myPosition.getHorizontalPosition(), pawn.myPosition.getVerticalPosition()));
            fieldsToTravel.add(temp.findField(pawn.myPosition.getHorizontalPosition()+moves[i][0], pawn.myPosition.getVerticalPosition()+(moves[i][1]*pawn.moveDirection)));
            if(fieldsToTravel.get(fieldsToTravel.size()-1) != null){
                temp.addFields(fieldsToTravel);
                fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)){
                    temp.swapPieces(fieldsToTravel);
                    temp.makeUnchecking();
                    temp.makeChecking();
                    if(!temp.isMyKingChecked(color))
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isKnightInMate(Knight knight,Board chessboard, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[] ones = new int[]{-1, 1};
        int[] twos = new int[]{-2, 2};

        for (int i = 0; i < ones.length; i++){
            for(int j = 0; j < twos.length; j++) {
                Board temp = new Board(chessboard); temp.fixPositions();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition(), knight.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition() + ones[i], knight.myPosition.getVerticalPosition() + twos[j]));
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if (fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)) {
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if (!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }

        for (int i = 0; i < ones.length; i++){
            for(int j = 0; j < twos.length; j++) {
                Board temp = new Board(chessboard); temp.fixPositions();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition(), knight.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition() + twos[i], knight.myPosition.getVerticalPosition() + ones[j]));
                fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                    if (fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)) {
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if (!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isBishopInMate(Bishop bishop,Board chessboard, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        for (int i = 0; i < chessboard.fields.length; i++){
            Board temp = new Board(chessboard); temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(bishop.myPosition.getHorizontalPosition(), bishop.myPosition.getVerticalPosition()));
            for (int j = 0; j < temp.fields[i].getPiecesCheckingMe().size(); j++){
                if(temp.fields[i].getPiecesCheckingMe().get(j).equals(bishop)){
                    fieldsToTravel.add(temp.fields[i]);
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isRookInMate(Rook rook,Board chessboard, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        for (int i = 0; i < chessboard.fields.length; i++){
            Board temp = new Board(chessboard); temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(rook.myPosition.getHorizontalPosition(), rook.myPosition.getVerticalPosition()));
            for (int j = 0; j < temp.fields[i].getPiecesCheckingMe().size(); j++){
                if(temp.fields[i].getPiecesCheckingMe().get(j).equals(rook)){
                    fieldsToTravel.add(temp.fields[i]);
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isQueenInMate(Queen queen,Board chessboard, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        for (int i = 0; i < chessboard.fields.length; i++){
            Board temp = new Board(chessboard); temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(queen.myPosition.getHorizontalPosition(), queen.myPosition.getVerticalPosition()));
            for (int j = 0; j < temp.fields[i].getPiecesCheckingMe().size(); j++){
                if(temp.fields[i].getPiecesCheckingMe().get(j).equals(queen)){
                    fieldsToTravel.add(temp.fields[i]);
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isKingInMate(King king, Board chessboard, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[] ones = new int[]{-1,0,1};
        for (int i = 0; i < ones.length; i++){
            for (int j = 0; j < ones.length; j++){
                if(ones[i] == 0 && ones[j] == 0)
                    continue;
                Board temp = new Board(chessboard); temp.fixPositions();
                int myVerticalPosition = king.myPosition.getVerticalPosition();
                int myHorizontalPosition = king.myPosition.getHorizontalPosition();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(king.myPosition.getHorizontalPosition(), king.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(myHorizontalPosition+ones[i], myVerticalPosition+ones[j]));
                if(fieldsToTravel.get(fieldsToTravel.size()-1) != null){
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if(fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField() == null){
                        if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)){
                            temp.swapPieces(fieldsToTravel);
                            temp.makeUnchecking();
                            temp.makeChecking();
                            if(!temp.isMyKingChecked(color))
                                return false;
                        }
                    }
                    else if(fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField().getPieceColor() != color){
                        if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)){
                            temp.swapPieces(fieldsToTravel);
                            temp.makeUnchecking();
                            temp.makeChecking();
                            if(!temp.isMyKingChecked(color))
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}