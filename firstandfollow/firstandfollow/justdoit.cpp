#include<iostream>
#include<fstream>
#include<String>
#include"type.h"
using namespace std;

string fzjf = {};
string zjf = {};
list<char> firstvt;
list<char> followvt;
char canBe;//������ȡfirst����������������һ������ķ��ս��
char doingF;
string canFo;//���ڴ���follow����ÿһ��������������һ������ķ��ս���ļ���
list<flist> cFo;//���ڴ洢follow�������������ʹ�ù���flist��Ŀ
list<flist> sth;//����ʽ���󼯺�
bool Wflag = true;//�����ж�ǰһ�������Ƿ��пմ�,follow������ʹ��
bool Tflag = true;//ͬ��,first������ʹ��

bool isInfzjf(char a)//�ж��Ƿ��ڷ��ս��������
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
bool isInzjf(char a)//�ж��Ƿ����ս��������
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

void FIRST(string fi,list<flist> sth)//first���Ϲ��ܺ���
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
				if (!Tflag)//����ոռ����first������û�пմ�,ֹͣѭ��
				{
	
					break;
				}
			}
		}
	}
	return;
}
void creatFirst()//first���������������
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
		//����Բ�����Ϊ�յķ��ս����β,��ɾ��$
		if ((isInfzjf(canBe) && !fo.canBeEmpty(c,sth) && (fo.body.length() > 2)))
		{
			//cout << fo.body.length();
			firstvt.remove('$');
		}
		//������ս����β,��head������ȡ��,ɾ��$
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

void FOLLOW(string fi, list<flist> sth)//follow���Ϲ��ܺ���
{
	flist fo;
	string a = {};
	string b = {};
	/*string fii = {};
	fii = fzjf[0];*/
	list<flist>::iterator its;
	//�����ǰ����ķ��ս����ĳ��ʽ�ӵ����һλ,��ֱ�Ӽ������ʽ��ͷ��follow����
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
	//	followvt.push_back('$');//$Ԥ�ȼ��뼯����
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
					if (!Wflag)//����ոռ����first������û�пմ�,ֹͣѭ��
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

void createFollow()//follow�����������
{
	for (int i = 0; i < fzjf.length(); i++)
	{
		int k = 0;
		flist fo;
		flist cFof;
		string b = {};
		b = fzjf[i];
		followvt.push_back('$');//$Ԥ�ȼ��뼯����
		doingF = b[0];
		FOLLOW(b, sth);
		list<flist>::iterator itcf;
		for (itcf = cFo.begin(); itcf != cFo.end(); itcf++)//�����ж�follow�����ĵ���������
		{
			cFof = *itcf;
			string c = {};
			string co = {};
			co = canFo[k];
			c = doingF;
			fo = fo.getHead(c, sth);
			//����Ĳ����������ֹ��ѭ��,��follow�����㵽���һ�����ս���ҿ���Ϊ��,��ϲ�
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
		followvt.unique();//ȥ��
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

void fileReader(string x)//�ļ���ȡ����
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
	cout << "FIRST����" << endl;
	creatFirst();
	cout << "FOLLOW����" << endl;
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
		cout << "�����ļ���" << endl;
		cin >> x;
		x += ".txt";
		if (x == "quit")
			break;
		justdoit(x);
	}
	system("pause");
}