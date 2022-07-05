package pl.piotrkorys.chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckmateController {
    public static boolean isCheckMate(Board chessboard, String color) {
        Field[] fields = chessboard.getFields();
        chessboard.fixPositions();
        List<Field> fieldsWithPieces = Arrays.stream(fields).filter(field -> field.getPieceAtField() != null && field.getPieceAtField().getPieceColor().equals(color)).toList();
        for (Field field : fieldsWithPieces) {
            Piece pieceOnField = field.getPieceAtField();
            switch (pieceOnField.getPieceType()) {
                case "pawn" -> {
                    if (!isPawnInMate((Pawn) pieceOnField, chessboard, color))
                        return false;
                }
                case "knight" -> {
                    if (!isKnightInMate((Knight) pieceOnField, chessboard, color))
                        return false;
                }
                case "bishop" -> {
                    if (!isBishopInMate((Bishop) pieceOnField, chessboard, color))
                        return false;
                }
                case "rook" -> {
                    if (!isRookInMate((Rook) pieceOnField, chessboard, color))
                        return false;
                }
                case "queen" -> {
                    if (!isQueenInMate((Queen) pieceOnField, chessboard, color))
                        return false;
                }
                case "King" -> {
                    if (!isKingInMate((King) pieceOnField, chessboard, color))
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isPawnInMate(Pawn pawn, Board chessboard, String color) {
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[][] moves = new int[][]{{0, 1}, {0, 2}, {-1, 1}, {1, 1}};
        for (int[] move : moves) {
            Board temp = new Board(chessboard);
            temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(pawn.myPosition.getHorizontalPosition(), pawn.myPosition.getVerticalPosition()));
            fieldsToTravel.add(temp.findField(pawn.myPosition.getHorizontalPosition() + move[0], pawn.myPosition.getVerticalPosition() + (move[1] * pawn.moveDirection)));
            if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                temp.addFields(fieldsToTravel);
                Piece.setChessboard(temp);
                boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                if (!swapResult)
                    return false;
            }
        }
        return true;
    }

    private static boolean isKnightInMate(Knight knight, Board chessboard, String color) {
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[] ones = new int[]{-1, 1};
        int[] twos = new int[]{-2, 2};

        for (int one : ones) {
            for (int two : twos) {
                Board temp = new Board(chessboard);
                temp.fixPositions();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition(), knight.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition() + one, knight.myPosition.getVerticalPosition() + two));
                Piece.setChessboard(temp);
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                    boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                    if (!swapResult)
                        return false;
                }
            }
        }

        for (int i = 0; i < ones.length; i++) {
            for (int j = 0; j < twos.length; j++) {
                Board temp = new Board(chessboard);
                temp.fixPositions();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition(), knight.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition() + twos[i], knight.myPosition.getVerticalPosition() + ones[j]));
                Piece.setChessboard(temp);
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                    boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                    if (!swapResult)
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isBishopInMate(Bishop bishop, Board chessboard, String color) {
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        Field[] fields = chessboard.getFields();
        for (int i = 0; i < fields.length; i++) {
            Board temp = new Board(chessboard);
            temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(bishop.myPosition.getHorizontalPosition(), bishop.myPosition.getVerticalPosition()));
            Field[] tempFields = temp.getFields();
            for (Piece piece : tempFields[i].getPiecesCheckingMe()) {
                if (piece.equals(bishop)) {
                    fieldsToTravel.add(tempFields[i]);
                    temp.addFields(fieldsToTravel);
                    Piece.setChessboard(temp);
                    boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                    if (!swapResult)
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isRookInMate(Rook rook, Board chessboard, String color) {
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        Field[] fields = chessboard.getFields();
        for (int i = 0; i < fields.length; i++) {
            Board temp = new Board(chessboard);
            temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(rook.myPosition.getHorizontalPosition(), rook.myPosition.getVerticalPosition()));
            Field[] tempFields = temp.getFields();
            for (int j = 0; j < tempFields[i].getPiecesCheckingMe().size(); j++) {
                if (tempFields[i].getPiecesCheckingMe().get(j).equals(rook)) {
                    fieldsToTravel.add(tempFields[i]);
                    temp.addFields(fieldsToTravel);
                    Piece.setChessboard(temp);
                    boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                    if (!swapResult)
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isQueenInMate(Queen queen, Board chessboard, String color) {
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        Field[] fields = chessboard.getFields();
        for (int i = 0; i < fields.length; i++) {
            Board temp = new Board(chessboard);
            temp.fixPositions();
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(queen.myPosition.getHorizontalPosition(), queen.myPosition.getVerticalPosition()));
            Field[] tempFields = temp.getFields();
            for (int j = 0; j < tempFields[i].getPiecesCheckingMe().size(); j++) {
                if (tempFields[i].getPiecesCheckingMe().get(j).equals(queen)) {
                    fieldsToTravel.add(tempFields[i]);
                    temp.addFields(fieldsToTravel);
                    Piece.setChessboard(temp);
                    boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                    if (!swapResult)
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isKingInMate(King king, Board chessboard, String color) {
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[] ones = new int[]{-1, 0, 1};
        for (int j : ones) {
            for (int one : ones) {
                if (j == 0 && one == 0)
                    continue;
                Board temp = new Board(chessboard);
                temp.fixPositions();
                int myVerticalPosition = king.myPosition.getVerticalPosition();
                int myHorizontalPosition = king.myPosition.getHorizontalPosition();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(king.myPosition.getHorizontalPosition(), king.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(myHorizontalPosition + j, myVerticalPosition + one));
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                    temp.addFields(fieldsToTravel);
                    Piece.setChessboard(temp);
                    if (fieldsToTravel.get(fieldsToTravel.size() - 1).getPieceAtField() == null) {
                        boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                        if (!swapResult)
                            return false;
                    } else if (fieldsToTravel.get(fieldsToTravel.size() - 1).getPieceAtField().getPieceColor() != color) {
                        boolean swapResult = willCheckAfterSwap(temp, fieldsToTravel, color);
                        if (!swapResult)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean willCheckAfterSwap(Board temp, List<Field> fieldsToTravel, String color) {
        if (fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, true)) {
            temp.swapPieces(fieldsToTravel);
            temp.makeUnchecking();
            temp.makeChecking();
            return temp.isMyKingChecked(color);
        }
        return false;
    }
}