/*=======================================================================================
	Author:			Holly Eaton

	Dev Envi:		Android Studio, eclipse Android Development Tools and Notepad++

	Program:		Crystal Ball App (Magic Eight ball game)

	Description:	This file contains the answer array and the random answer generator.
 
 ========================================================================================*/

package com.example.crystalball;

import java.util.Random;

public class CrystalBall
{
	//Member variables (properties about the object)
	public String[] mAnswersArray = 
	{
			"It is certain", 
			"It is decidedly so", 
			"All signs say YES",
			"Yes",
			"Absolutely",
			"Definitely",
			"The stars are not aligned",
			"My reply is no",
			"It is doubtful",
			"Better not tell you now",
			"Concentrate and ask again",
			"Unable to answer now",
			"It is hard to say"
	};
	
	//Methods (abilities: things the object can do)
	public String getAnAnswer() 
	{	
		String answer = "";
		
		Random randomGenerator = new Random(); //Construct a new Random number generator
		int randomNumber = randomGenerator.nextInt(mAnswersArray.length); /*Use it to generate a random integer
		that matches one of the index numbers of the array, storing it into the int variable "randomNumber".*/
		
		answer = mAnswersArray[randomNumber];
		
		return answer;
	}
}
