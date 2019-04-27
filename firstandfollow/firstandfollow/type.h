#pragma once
#include<list>
#include<iostream>
#include<string>
using namespace std;

class flist
{
public:
	string head;
	string body;
	flist() { head = {}; body = {}; }
	flist getBody(string headin, list<flist> sth)
	{
		flist fo;
		list<flist>::iterator ito;
		for (ito = sth.begin(); ito != sth.end(); ito++)
		{
			fo = *ito;
			if (fo.head == headin)
				break;
		}
		return fo;
	}
	flist getHead(string bodyin, list<flist> sth)
	{
		flist fo;
		list<flist>::iterator ito;
		for (ito = sth.begin(); ito != sth.end(); ito++)
		{
			fo = *ito;
			for (int k = 0; k < fo.body.length() - 1; k++)
			{
				if (fo.body[k] == bodyin[0])
					return fo;
			}
		}
	}
	bool canBeEmpty(string headin, list<flist> sth)
	{
		flist fo;
		list<flist>::iterator ito;
		for (ito = sth.begin(); ito != sth.end(); ito++)
		{
			fo = *ito;
			if (fo.head == headin)
			{
				if (fo.body[0] == '$')
				{
					return true;
				}
			}
		}
		return false;
	}
	bool canBeLast(flist cFof, char l)
	{
		if (cFof.body[cFof.body.length() - 2] == l)
		{
			return true;
		}
		return false;
	}
};