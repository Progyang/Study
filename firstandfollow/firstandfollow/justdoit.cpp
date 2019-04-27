#include<iostream>
#include<fstream>
#include<String>
#include"type.h"
using namespace std;

string fzjf = {};
string zjf = {};
list<char> firstvt;
list<char> followvt;
char canBe;//用于提取first函数计算过程中最后一个计算的非终结符
char doingF;
string canFo;//用于储存follow函数每一条运算过程中最后一个计算的非终结符的集合
list<flist> cFo;//用于存储follow函数计算过程中使用过的flist条目
list<flist> sth;//生成式对象集合
bool Wflag = true;//用于判断前一个集合是否含有空串,follow函数中使用
bool Tflag = true;//同上,first函数中使用

bool isInfzjf(char a)//判断是否在非终结符集合中
{
	for (int i = 0; i < fzjf.length(); i++)
	{
		if (a == fzjf[i])
		{
			return true;
		}
	}
	return false;
}
bool isInzjf(char a)//判断是否在终结符集合中
{
	for (int i = 0; i < zjf.length(); i++)
	{
		if (a == zjf[i])
		{
			return true;
		}
	}
	return false;
}

void FIRST(string fi,list<flist> sth)//first集合功能函数
{
	flist fo;
	string a = {};
	list<flist>::iterator it;
	for (it = sth.begin(); it != sth.end(); it++)
	{
		fo = *it;
		if (fo.head == fi)
		{
			for (int k = 0; k < fo.body.length() - 1; k++)
			{
				if (isInfzjf(fo.body[k]))
				{
					a = fo.body[k];
					canBe = fo.body[k];
					FIRST(a, sth);
				}
				if (isInzjf(fo.body[k]) || fo.body[k] == '$')
				{
					Tflag = false;
					firstvt.push_back(fo.body[k]);
					if (fo.body[k] == '$')
					{
						Wflag = true;
						Tflag = true;
					}
				}
				if (!Tflag)//如果刚刚加入的first集合中没有空串,停止循环
				{
	
					break;
				}
			}
		}
	}
	return;
}
void creatFirst()//first集合生成输出函数
{
	for (int i = 0; i < fzjf.length(); i++)
	{
		flist fo;
		string c = {};
		string b = {};
		b = fzjf[i];
		FIRST(b, sth);
		firstvt.sort();
		firstvt.unique();
		fo = fo.getBody(b, sth);
		c = canBe;
		//如果以不可以为空的非终结符结尾,则删除$
		if ((isInfzjf(canBe) && !fo.canBeEmpty(c,sth) && (fo.body.length() > 2)))
		{
			//cout << fo.body.length();
			firstvt.remove('$');
		}
		//如果以终结符结尾,且head不可以取空,删除$
		if ((isInzjf(fo.body[fo.body.length()-2]) && (fo.body.length() > 2)) && !fo.canBeEmpty(fo.head, sth))
		{
			firstvt.remove('$');
		}
		list<char>::iterator it;
		cout << b << ":{";
		for (it = firstvt.begin(); it != firstvt.end(); it++)
		{
			cout << *it << " ";
		}
		cout << "}" << endl;
		firstvt.clear();
		canBe = {};
	}
}

void FOLLOW(string fi, list<flist> sth)//follow集合功能函数
{
	flist fo;
	string a = {};
	string b = {};
	/*string fii = {};
	fii = fzjf[0];*/
	list<flist>::iterator its;
	//如果当前计算的非终结符是某个式子的最后一位,则直接计算这个式子头的follow集合
	for (its = sth.begin(); its != sth.end(); its++)
	{
		fo = *its;
		if (fo.canBeLast(fo, fi[0]))
		{
			doingF = fo.head[0];
			FOLLOW(fo.head, sth);
			return;
		}
	}
	//doingF = fi[0];
	list<flist>::iterator it;
	//if (fi == fii)
	//{
	//	followvt.push_back('$');//$预先加入集合中
	//}
	for (it = sth.begin(); it != sth.end(); it++)
	{
		fo = *it;
		for (int i = 0; i < fo.body.length() - 1; i++)
		{
			a = fo.body[i];
			if (fi == a && !fo.canBeLast(fo, fo.body[i]))
			{
				for (int k = i + 1; k < fo.body.length() - 1; k++)
				{
					Wflag = false;
					if (isInfzjf(fo.body[k]))
					{
						canBe = fo.body[k];
						b = fo.body[k];
						FIRST(b, sth);
					}
					if (isInzjf(fo.body[k]) || fo.body[k] == '$')
					{
						followvt.push_back(fo.body[k]);
					}
					if (!Wflag)//如果刚刚加入的first集合中没有空串,停止循环
					{
						break;
					}
				}
				canFo += canBe;
				cFo.push_back(fo);
			}
			/*if (fi == a && fo.canBeLast(fo, fo.body[i]))
			{
				FOLLOW(fo.head, sth);
			}*/
		}
	}
	return;
}

void createFollow()//follow集合输出函数
{
	for (int i = 0; i < fzjf.length(); i++)
	{
		int k = 0;
		flist fo;
		flist cFof;
		string b = {};
		b = fzjf[i];
		followvt.push_back('$');//$预先加入集合中
		doingF = b[0];
		FOLLOW(b, sth);
		list<flist>::iterator itcf;
		for (itcf = cFo.begin(); itcf != cFo.end(); itcf++)//用于判断follow函数的第三条规则
		{
			cFof = *itcf;
			string c = {};
			string co = {};
			co = canFo[k];
			c = doingF;
			fo = fo.getHead(c, sth);
			//算过的不可以再算防止死循环,若follow计算算到最后一个非终结符且可以为空,则合并
			if (fo.head[0] != doingF && cFof.head[0]!=doingF && cFof.canBeLast(cFof, canFo[k]) && cFof.canBeEmpty(co, sth))
			{
				FOLLOW(cFof.head, sth);
				break;
			}
			k++;
		}
		cFo.clear();
		canFo = {};
		canBe = {};
		firstvt.unique();
		followvt.unique();//去重
		list<char>::iterator it;
		for (it = firstvt.begin(); it != firstvt.end(); it++)
		{
			followvt.push_back(*it);
		}
		followvt.sort();
		followvt.unique();
		//cout << canFo;
		//if (cFo.empty()) {};
		list<char>::iterator itw;
		cout << b << ":{";
		for (itw = followvt.begin(); itw != followvt.end(); itw++)
		{
			cout << *itw << " ";
		}
		cout << "}" << endl;
		followvt.clear();
		firstvt.clear();
		doingF = {};
	}
}

void fileReader(string x)//文件读取函数
{
	string a = {};
	fstream file;
	file.open(x, ios_base::in | ios_base::out);
	if (!file)
	{
		cout << "error" << endl;
	}
	else
	{
		getline(file, a);
		for (int i = 0; i <= a.length() - 1; i++)
		{
			fzjf += a[i];
		}
		cout << fzjf << endl;
		getline(file, a);
		for (int i = 0; i <= a.length() - 1; i++)
		{
			zjf += a[i];
		}
		cout << zjf << endl;
		while (getline(file, a))
		{
			flist ff;
			ff.head = a[0];
			for (int i = 3; i <= a.length(); i++)
			{
				ff.body += a[i];
			}
			sth.push_back(ff);
		}
		for (flist f : sth)
		{		
			cout << f.head << "->" << f.body << endl;
		}
	}
}

void justdoit(string x)
{
	fileReader(x);
	cout << "FIRST集合" << endl;
	creatFirst();
	cout << "FOLLOW集合" << endl;
	createFollow();
	sth.clear();
	fzjf = {};
	zjf = {};
}

void main()
{
	string x = {};
	while (1)
	{
		cout << "输入文件名" << endl;
		cin >> x;
		x += ".txt";
		if (x == "quit")
			break;
		justdoit(x);
	}
	system("pause");
}