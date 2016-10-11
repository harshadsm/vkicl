package vkicl.exceptions;

public class CutNotPossibleException extends Exception {

	CutNotPossibleException(Exception e){
		super(e);
	}
	
	public CutNotPossibleException(String error) {
		super(error);
	}
}
