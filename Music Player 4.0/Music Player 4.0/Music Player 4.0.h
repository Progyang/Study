
// Music Player 2.0.h : PROJECT_NAME Ӧ�ó������ͷ�ļ�
//

#pragma once

#ifndef __AFXWIN_H__
	#error "�ڰ������ļ�֮ǰ������stdafx.h�������� PCH �ļ�"
#endif

#include "resource.h"		// ������


// CMusicPlayer20App: 
// �йش����ʵ�֣������ Music Player 2.0.cpp
//

class CMusicPlayer20App : public CWinApp
{
public:
	CMusicPlayer20App();

// ��д
public:
	virtual BOOL InitInstance();

// ʵ��

	DECLARE_MESSAGE_MAP()
};

extern CMusicPlayer20App theApp;
