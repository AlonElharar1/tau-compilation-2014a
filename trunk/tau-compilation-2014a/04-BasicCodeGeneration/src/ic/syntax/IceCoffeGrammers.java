/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */
package ic.syntax;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class IceCoffeGrammers {

	public static final String IC_LIB_CFG_FILE = "./grammers/IC.cfg";
	public static final String IC_CFG_FILE = "./grammers/ICLIB.cfg";
	
	public static final String IC_LIB_CFG =
			"S -> Program\nProgram -> CLASS\nCLASS -> class CLASS_ID { CLASS_CONTENT }\nCLASS_CONTENT -> METHOD CLASS_CONTENT | METHOD\nMETHOD -> METHODLIB\nMETHODLIB -> static METHOD_HEADER ;\nMETHOD_HEADER -> TYPE ID METHOD_PARAMS\nMETHOD_PARAMS -> ( ) | ( FORMALS )\nFORMALS -> PARAMETER | PARAMETER , FORMALS\nPARAMETER -> TYPE ID\nTYPE -> TYPE [ ] | PRIMITIVE | CLASS_TYPE\nCLASS_TYPE -> CLASS_ID\nPRIMITIVE -> int | boolean | string | void";
	
	public static final String IC_CFG = 
			"S -> Program\nProgram -> CLASS Program | CLASS\nCLASS -> class CLASS_ID { CLASS_CONTENT } | class CLASS_ID extends CLASS_ID { CLASS_CONTENT }\nCLASS_CONTENT -> FIELD CLASS_CONTENT | METHOD CLASS_CONTENT | FIELD | METHOD\nFIELD -> TYPE IDS ;\nIDS -> ID , IDS | ID\nMETHOD -> METHODSTATIC | METHODVIRTUAL\nMETHODSTATIC -> static METHOD_HEADER\nMETHODVIRTUAL -> METHOD_HEADER\nMETHOD_HEADER -> TYPE ID METHOD_PARAMS METHOD_CONTENT\nMETHOD_CONTENT -> { } | { STMTS }\nSTMTS -> STMT STMTS | STMT\nMETHOD_PARAMS -> ( ) | ( FORMALS )\nTYPE -> TYPE [ ] | PRIMITIVE | CLASS_TYPE\nCLASS_TYPE -> CLASS_ID\nPRIMITIVE -> int | boolean | string | void\nFORMALS -> PARAMETER | PARAMETER , FORMALS\nPARAMETER -> TYPE ID\nSTMT -> STMTIF\nNON_IF_STMT -> STMTASSIGN | STMTCALL | STMTRETURN | STMTWHILE | STMTBREAK | STMTCONTINUE | STMTBLOCK | STMTLOCALVAR\nSTMTASSIGN -> LOCATION = EXPR ;\nSTMTCALL -> CALL ;\nSTMTRETURN -> return ; | return EXPR ;\nSTMTIF -> MATCHED_IF | UNMATCHED_IF\nMATCHED_IF -> if ( EXPR ) MATCHED_IF else MATCHED_IF | NON_IF_STMT\nUNMATCHED_IF -> if ( EXPR ) STMT | if ( EXPR ) MATCHED_IF else UNMATCHED_IF\nSTMTWHILE -> while ( EXPR ) STMT\nSTMTBREAK -> break ;\nSTMTCONTINUE -> continue ;\nSTMTBLOCK -> { STMTS }\nSTMTLOCALVAR -> TYPE ID ; | TYPE ID = EXPR ;\nEXPR -> EXPRBINOP\nEXPRCLOSE -> CALL | EXPRBLOCK | LITERAL | EXPRTHIS | LOCATION | EXPRLEN\nNO_BINOP_EXPR -> EXPRCLOSE | EXPRNEW\nUNOP -> - | !\nEQOP -> == | !=\nRELOP -> < | <= | > | >=\nADDOP -> + | -\nMULTOP -> * | / | %\nEXPRBINOP -> EXPRBINOP || EXPRBINOP_AND | EXPRBINOP_AND \nEXPRBINOP_AND -> EXPRBINOP_AND && EXPRBINOP_EQ | EXPRBINOP_EQ\nEXPRBINOP_EQ -> EXPRBINOP_EQ EQOP EXPRBINOP_RELATIONAL | EXPRBINOP_RELATIONAL\nEXPRBINOP_RELATIONAL -> EXPRBINOP_RELATIONAL RELOP EXPRBINOP_ADD  | EXPRBINOP_ADD\nEXPRBINOP_ADD -> EXPRBINOP_ADD ADDOP EXPRBINOP_MULT | EXPRBINOP_MULT\nEXPRBINOP_MULT -> EXPRBINOP_MULT MULTOP EXPRUNOP | EXPRUNOP\nEXPRUNOP -> UNOP EXPRUNOP | NO_BINOP_EXPR\nCALL -> STATIC_CALL | VIRTUAL_CALL\nSTATIC_CALL -> CLASS_ID . ID ( ) | CLASS_ID . ID ( EXPRS )\nVIRTUAL_CALL -> EXPRCLOSE . ID ( ) | ID ( ) | EXPRCLOSE . ID ( EXPRS ) | ID ( EXPRS )\nEXPRS -> EXPR | EXPR , EXPRS\nEXPRBLOCK -> ( EXPR )\nEXPRLEN -> EXPRCLOSE . length\nLITERAL -> INTEGER | STRING | true | false | null\nEXPRNEW -> EXPRNEWARRAY | EXPRNEWINS\nEXPRNEWARRAY -> new TYPE [ EXPR ]\nEXPRNEWINS -> new CLASS_ID ( )\nLOCATION -> REFARRAY | REFFIELD | REFVAR\nREFARRAY -> EXPRCLOSE [ EXPR ]\nREFFIELD -> EXPRCLOSE . ID\nREFVAR -> ID\nEXPRTHIS -> this";

	public static String getIceCoffeGrammer(boolean isLibrary) throws FileNotFoundException {

		boolean fromConstants = false;
		
		if (fromConstants) {
			return (isLibrary ? IceCoffeGrammers.IC_CFG : IceCoffeGrammers.IC_LIB_CFG);
		}
		else {
			
			Scanner fileScanner = new Scanner(isLibrary ? IceCoffeGrammers.IC_CFG_FILE : 
				IceCoffeGrammers.IC_LIB_CFG_FILE);
			
			try {
				return (fileScanner.useDelimiter("\\Z").next());
			}
			finally {
				if (fileScanner != null)
					fileScanner.close();
			}
		}
	}
	
}
