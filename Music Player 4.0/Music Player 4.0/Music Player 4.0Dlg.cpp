
// MusicPlayerDlg.cpp : 实现文件 
// 

#include "stdafx.h" 
#include "Music Player 4.0.h" 
#include "Music Player 4.0Dlg.h" 
#include "afxdialogex.h" 
#include <mmsystem.h> 
#include <locale.h> 
#pragma comment(lib,"winmm.lib") 
#ifdef _DEBUG 
#define new DEBUG_NEW 
#endif 

#define TIMERPLAYSTEP 1 
// 用于应用程序“关于”菜单项的 CAboutDlg 对话框 
int CMusicPlayerDlg::nextandlast = 0;
int CMusicPlayerDlg::counter = 0;
int CMusicPlayerDlg::check1 = 0;
int CMusicPlayerDlg::check2 = 0;
class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

	// 对话框数据 
#ifdef AFX_DESIGN_TIME 
	enum { IDD = IDD_ABOUTBOX };
#endif 

protected:
	virtual void DoDataExchange(CDataExchange* pDX); // DDX/DDV 支持 

													 // 实现 
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(IDD_ABOUTBOX)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CMusicPlayerDlg 对话框 



CMusicPlayerDlg::CMusicPlayerDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(IDD_MUSICPLAYER20_DIALOG, pParent)
	, m_strPath(_T(""))
	, m_strNowPlayPath(_T(""))
	, m_strVolume(_T("50/100"))
	, m_strStep(_T("0:00/0:00"))
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMusicPlayerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);

	DDX_Text(pDX, IDC_STATICNOWPLAYPATH, m_strNowPlayPath);
	DDX_Control(pDX, IDPLAY, m_btnPlay);
	DDX_Control(pDX, IDC_LISTMUSIC, m_ctrMusicList);
	DDX_Text(pDX, IDC_STATICVOLUME, m_strVolume);
	DDX_Text(pDX, IDC_STATICSTEP, m_strStep);
	DDX_Control(pDX, IDC_SLIDERSTEP, m_ctrStep);
	DDX_Control(pDX, IDC_SLIDERVOLUME, m_ctrVolume);
}

BEGIN_MESSAGE_MAP(CMusicPlayerDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDPLAY, &CMusicPlayerDlg::OnClickedIdplay)
	ON_BN_CLICKED(IDSTOP, &CMusicPlayerDlg::OnClickedIdstop)
	ON_COMMAND(ID_MENUADDFILE, &CMusicPlayerDlg::OnMenuaddfile)
	ON_COMMAND(ID_APP_ABOUT, &CMusicPlayerDlg::OnAppAbout)
	ON_COMMAND(ID_MENUEXIT, &CMusicPlayerDlg::OnMenuexit)
	ON_NOTIFY(LVN_ITEMCHANGED, IDC_LISTMUSIC, &CMusicPlayerDlg::OnLvnItemchangedListmusic)
	ON_NOTIFY(NM_DBLCLK, IDC_LISTMUSIC, &CMusicPlayerDlg::OnDblclkListmusic)
	ON_NOTIFY(NM_RCLICK, IDC_LISTMUSIC, &CMusicPlayerDlg::OnRclickListmusic)
	ON_BN_CLICKED(IDC_BUTTONLAST, &CMusicPlayerDlg::OnBnClickedButtonlast)
	ON_COMMAND(ID_MENUCLEAR, &CMusicPlayerDlg::OnMenuclear)
	ON_WM_TIMER()
	ON_NOTIFY(NM_CUSTOMDRAW, IDC_SLIDERSTEP, &CMusicPlayerDlg::OnNMCustomdrawSliderstep)
	ON_NOTIFY(NM_CUSTOMDRAW, IDC_SLIDERVOLUME, &CMusicPlayerDlg::OnNMCustomdrawSlidervolume)
	ON_BN_CLICKED(IDC_BUTTONNEXT, &CMusicPlayerDlg::OnBnClickedButtonnext)
	ON_BN_CLICKED(IDC_CHECK1, &CMusicPlayerDlg::OnBnClickedCheck1)
	ON_BN_CLICKED(IDC_CHECK2, &CMusicPlayerDlg::OnBnClickedCheck2)
	ON_COMMAND(ID_MENURED, &CMusicPlayerDlg::OnMenured)
	ON_COMMAND(ID_MENUPURPLE, &CMusicPlayerDlg::OnMenupurple)
	ON_COMMAND(ID_MENUWHITE, &CMusicPlayerDlg::OnMenuwhite)
	ON_COMMAND(ID_MENUGRAY, &CMusicPlayerDlg::OnMenugray)
	ON_COMMAND(ID_MENUGREEN, &CMusicPlayerDlg::OnMenugreen)
	ON_COMMAND(ID_32788, &CMusicPlayerDlg::On32788)
END_MESSAGE_MAP()


// CMusicPlayerDlg 消息处理程序 

BOOL CMusicPlayerDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// 将“关于...”菜单项添加到系统菜单中。 

	// IDM_ABOUTBOX 必须在系统命令范围内。 
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 设置此对话框的图标。 当应用程序主窗口不是对话框时，框架将自动 
	// 执行此操作 
	SetIcon(m_hIcon, TRUE);// 设置大图标 
	SetIcon(m_hIcon, FALSE);// 设置小图标 

							// TODO: 在此添加额外的初始化代码 
	m_PlayStatus = 0;
	this->SetBackgroundColor(RGB(175, 238, 238));
	ReadFileToList();
	m_ctrVolume.SetRange(0, 100);
	m_ctrVolume.SetPos(m_nowPlayVolume);
	return TRUE; // 除非将焦点设置到控件，否则返回 TRUE 
}

void CMusicPlayerDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// 如果向对话框添加最小化按钮，则需要下面的代码 
// 来绘制该图标。 对于使用文档/视图模型的 MFC 应用程序， 
// 这将由框架自动完成。 

void CMusicPlayerDlg::OnPaint()
{

	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文 

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作区矩形中居中 
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标 
		dc.DrawIcon(x, y, m_hIcon);
	}
	
	else
	{
		CDialogEx::OnPaint();
	}

}

//当用户拖动最小化窗口时系统调用此函数取得光标 
//显示。 
HCURSOR CMusicPlayerDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMusicPlayerDlg::OnClickedButtonsel()
{
	// TODO: 在此添加控件通知处理程序代码 
	/*
	CFileDialog MFC中的文件打开保存对话框，CFileDialog的构造函数原型为：

	*/
	//OFN_ALLOWMULTISELECT 允许多选标志 
	CFileDialog fdlg(TRUE, _T(".mp3"), NULL, OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT | OFN_ALLOWMULTISELECT, _T("歌曲(.mp3)|*.mp3||"), NULL);
	if (fdlg.DoModal() == IDOK)
	{
		POSITION pos = fdlg.GetStartPosition();
		while (pos != NULL)//遍历多选文件列表 
		{
			//在这里 GetNextPathName()函数有个BUG，当列表数目过多时，文件路径会获取错误，最终失败 
			CString FilePath = fdlg.GetNextPathName(pos);//获取文件对话框选择的文件全路径 
														 //MessageBox(FilePath); 
			AddMusicToList(FilePath);
		}

	}
}
void CMusicPlayerDlg::AddMusicToList(CString FilePath)//封装函数 添加音乐文件至列表框以及链表数据中 
{
	CString *path = new CString(FilePath);
	CString name;
	int index = FilePath.ReverseFind('\\');
	if (index > 0)
	{
		name = FilePath.Right(FilePath.GetLength() - index - 1);//截取文件名称 
		POSITION pos = m_MusicPathList.AddTail(*path);//音乐全路径添加至数据链表中 
		int count = m_ctrMusicList.GetItemCount();
		m_ctrMusicList.InsertItem(count, name);//将音乐名称插入列表项中 
		BOOL r = m_ctrMusicList.SetItemData(count, (DWORD_PTR)path);//绑定音乐全路径列表控件中 
	}
	//MessageBox(name); 
}
int CMusicPlayerDlg::StatusMessageDistribution(CString cmd, TCHAR* cmdParam, int ParamLen)//封装函数 音乐播放命令消息分配函数 
{
	//int result = -1; 
	if (cmd.IsEmpty())
		return -1;

	if (cmdParam == NULL)
	{
		cmd += _T(" music");
	}
	else
	{
		if (cmd.Compare(_T("open")) == 0)//打开文件 
		{
			cmd.Format(_T("open \"%s\" alias music"), cmdParam);
		}
		else if (cmd.Compare(_T("seek")) == 0)//文件定位 
		{
			cmd.Format(_T("seek music to %s"), cmdParam);
		}
		else if (cmd.Compare(_T("setaudio volume")) == 0)//设置声音 
		{
			cmd.Format(_T("setaudio music volume to %s"), cmdParam);
		}
		else if (cmd.Compare(_T("play")) == 0)//定位播放 
		{
			cmd.Format(_T("play music from %s"), cmdParam);
		}
		else if (cmd.Compare(_T("status")) == 0)//获取文件长度 
		{
			cmd.Format(_T("status music length"));
			memset(cmdParam, 0, MAX_PATH);
		}
		else
		{
			MessageBox(cmd + "命令无法识别");
		}
	}
	//MessageBox(cmd); 
	MCIERROR r = mciSendString(cmd, cmdParam, ParamLen, NULL);
	if (r == 0)
	{
		return 0;
	}
	else
	{
		//出错判断提示 
#if _DEBUG 
		TCHAR erStr[MAX_PATH] = _T("");
		mciGetErrorString(r, erStr, MAX_PATH);//如果文件打开遇到错误，输出错误提示语句 
		MessageBox(cmd + erStr);
#endif 
		return -1;
	}
	return -1;
}



void CMusicPlayerDlg::OnClickedIdplay()//音乐播放暂停 
{
	// TODO: 在此添加控件通知处理程序代码 
	int index = m_ctrMusicList.GetNextItem(-1, LVIS_SELECTED);//列表选项判断 
	//处理上一曲下一曲信息
	if (nextandlast == 1 || check2 == 1|| check1 == 1)
	{
		index += counter;
		nextandlast = 0;
	}
	//处理双击播放信息
	if (index >= 0)
	{
		if (index != m_nowPlayIndex && m_PlayStatus > 0)//判断是否播放新的音乐文件 
		{
			OnClickedIdstop();//停止当前播放音乐 
		}
		m_nowPlayIndex = index;
	}
	if (m_nowPlayIndex < 0) return;
	CString *path = NULL;
	TCHAR cmdPath[MAX_PATH] = _T("");

	//判断播放状态
	switch (m_PlayStatus)
	{
	case 0://打开音乐文件 
		path = (CString*)m_ctrMusicList.GetItemData(m_nowPlayIndex);
		wsprintf(cmdPath, _T("%s"), path->GetString());
		ASSERT(path != NULL);
		//MessageBox(*path); 
		if (StatusMessageDistribution(_T("open"), cmdPath) == 0)
		{
			m_PlayStatus = 1;
			m_nowPlayIndex = index;
			GetInfo();
			SetVolume();
		}
	case 1://文件打开状态播放音乐 
		if (StatusMessageDistribution(_T("play")) == 0)
		{
			
			m_PlayStatus = 2;
			m_strNowPlayPath = m_ctrMusicList.GetItemText(m_nowPlayIndex, 0);
			m_btnPlay.SetWindowTextW(_T("暂停"));//设置按钮文本显示为暂停 
			UpdateData(FALSE);
			SetTimer(TIMERPLAYSTEP, 1000, NULL);
		}
		break;
	case 2://文件播放状态暂停音乐 
		if (StatusMessageDistribution(_T("pause")) == 0)
		{
			m_PlayStatus = 3;
			//m_strNowPlayPath = _T("正在暂停歌曲！"); 
			m_btnPlay.SetWindowTextW(_T("播放"));//设置按钮文本显示为播放
											   //UpdateData(FALSE); 
		}
		break;
	case 3:
		if (StatusMessageDistribution(_T("resume")) == 0)
		{
			m_PlayStatus = 2;
			//m_strNowPlayPath = _T("正在播放歌曲！"); 
			m_btnPlay.SetWindowTextW(_T("暂停"));//设置按钮文本显示为暂停 
											   //UpdateData(FALSE); 
		}
		break;
	}
}


void CMusicPlayerDlg::OnClickedIdstop()//音乐停止 
{
	if (StatusMessageDistribution(_T("close")) == 0)
	{
		KillTimer(TIMERPLAYSTEP);
		m_PlayStatus = 0;//播放状态初始化 
		//m_nowPlayIndex = -1;//播放序号初始化 
		m_strNowPlayPath = (_T(""));//字符串提示情况 
		m_btnPlay.SetWindowTextW(_T("播放"));//设置按钮文本显示为播放
		//重置进度条
		m_nowPlayStep = 0;
		m_strStep = _T("0:00/0:00");
		UpdateData(FALSE);
	}
	// TODO: 在此添加控件通知处理程序代码 
}


void CMusicPlayerDlg::OnMenuaddfile()//添加音乐文件菜单响应函数 
{
	// TODO: 在此添加命令处理程序代码 
	OnClickedButtonsel();

}


void CMusicPlayerDlg::OnAppAbout()//关于菜单响应函数 
{
	// TODO: 在此添加命令处理程序代码 
	CAboutDlg dlgAbout;
	dlgAbout.DoModal();
}


void CMusicPlayerDlg::OnMenuexit()//程序退出响应函数 
{
	// TODO: 在此添加命令处理程序代码 
	SaveListToFile();
	OnMenuclear();
	PostQuitMessage(0);
	CDialogEx::OnDestroy();
}


void CMusicPlayerDlg::OnLvnItemchangedListmusic(NMHDR *pNMHDR, LRESULT *pResult)//列表选中项改变时触发的响应消息函数 
{
	LPNMLISTVIEW pNMLV = reinterpret_cast<LPNMLISTVIEW>(pNMHDR);
	// TODO: 在此添加控件通知处理程序代码 
	if (m_nowPlayIndex == pNMLV->iItem)
	{
		m_btnPlay.SetWindowText(_T("暂停"));
	}
	else
	{
		m_btnPlay.SetWindowText(_T("播放"));
	}
	*pResult = 0;
}


void CMusicPlayerDlg::OnDblclkListmusic(NMHDR *pNMHDR, LRESULT *pResult)//鼠标左键双击响应函数 
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: 在此添加控件通知处理程序代码 
	counter = 0;
	OnClickedIdplay();
	*pResult = 0;
}


void CMusicPlayerDlg::OnRclickListmusic(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: 在此添加控件通知处理程序代码 
	*pResult = 0;
}

void CMusicPlayerDlg::OnBnClickedButtonlast()//上一曲响应函数
{
	// TODO: 在此添加控件通知处理程序代码 
	nextandlast = 1;
	if (m_nowPlayIndex >0)//音乐边界判断 
	{
		//m_nowPlayIndex -= 1; 
		counter--;
		OnClickedIdplay();
	}
	else
	{
		MessageBox(_T("当前已处于第一首音乐!"));
	}
}


int CMusicPlayerDlg::SaveListToFile()//音乐文件列表保存 
{
	char* old_locale = _strdup(setlocale(LC_CTYPE, NULL));
	setlocale(LC_CTYPE, "chs");//设定为中文环境，解决UNICODE环境下中文字符储存问题 

	CStdioFile file;
	BOOL r = file.Open(_T("musiclist.dat"), CStdioFile::modeCreate | CStdioFile::modeWrite);
	if (r == FALSE)
	{
		MessageBox(L"文件写入失败!");
		setlocale(LC_CTYPE, old_locale);
		free(old_locale);//还原区域设定 
		return -1;
	}

	TCHAR wtBuffer[1024] = L"";
	int count = m_ctrMusicList.GetItemCount();
	for (int i = 0; i < count; i++)
	{
		CString *path = (CString*)m_ctrMusicList.GetItemData(i);
		ASSERT(path != NULL);
		wsprintf(wtBuffer, L"%s\n", path->GetString());
		file.WriteString(wtBuffer);
	}
	file.Close();
	//文件操作结束后，还原 
	setlocale(LC_CTYPE, old_locale);
	free(old_locale);//还原区域设定 
	return 0;
}


int CMusicPlayerDlg::ReadFileToList()//音乐文件列表还原 
{
	//设定为中文环境，解决UNICODE环境下中文字符储存问题 
	char* old_locale = _strdup(setlocale(LC_CTYPE, NULL));
	setlocale(LC_CTYPE, "chs");
	CStdioFile file;
	BOOL r = file.Open(_T("musiclist.dat"), CStdioFile::modeRead);
	if (r == FALSE)
	{
		setlocale(LC_CTYPE, old_locale);
		free(old_locale);//还原区域设定 
		MessageBox(L"文件读取失败!");
		return -1;
	}
	TCHAR rdBuffer[1024] = L"";
	TCHAR name[MAX_PATH] = L"";
	TCHAR *path = NULL;
	while (file.ReadString(rdBuffer, 1024))
	{
		int size = lstrlen(rdBuffer);
		rdBuffer[size - 1] = '\0';
		AddMusicToList(rdBuffer);
	}
	file.Close();

	//文件操作结束后，还原 
	setlocale(LC_CTYPE, old_locale);
	free(old_locale);//还原区域设定 
	return 0;
}


void CMusicPlayerDlg::OnMenuclear()//清空列表 
{
	// TODO: 在此添加命令处理程序代码 
	if (m_PlayStatus > 0)
	{
		OnClickedIdstop();
		StatusMessageDistribution(_T("close"));
	}
	m_ctrMusicList.DeleteAllItems();
	int count = m_MusicPathList.GetCount();
	for (int i = count; i > 0 ; i--)
	{
		m_MusicPathList.RemoveTail();
	}
}


int CMusicPlayerDlg::GetInfo()//获取音乐文件信息 
{
	TCHAR cmd[MAX_PATH] = L"status";
	TCHAR szlen[MAX_PATH] = L"";
	StatusMessageDistribution(cmd, szlen, MAX_PATH);
	m_nowPlayLength = _wtoi(szlen);
	m_nowPlayLength /= 1000;
	m_strStep = _T("0:00/0:00");
	m_ctrStep.SetRange(0, m_nowPlayLength);
	m_ctrStep.SetPos(0);
	m_strStep.Format(L"%2d:%2d/%2d:%2d", 0, 00, m_nowPlayLength / 60, m_nowPlayLength % 60);
	UpdateData(FALSE);
	return 0;
}


void CMusicPlayerDlg::OnTimer(UINT_PTR nIDEvent)//定时器响应函数 
{
	// TODO: 在此添加消息处理程序代码和/或调用默认值 
	switch (nIDEvent)
	{
	case TIMERPLAYSTEP://进度条变化 
		if (m_PlayStatus == 2)
		{
			if (m_nowPlayStep < m_nowPlayLength)
			{
				m_nowPlayStep += 1;
				m_strStep.Format(L"%2d:%2d/%2d:%2d", m_nowPlayStep / 60, m_nowPlayStep % 60, m_nowPlayLength / 60, m_nowPlayLength % 60);
				m_ctrStep.SetPos(m_nowPlayStep);
				UpdateData(FALSE);
			}
			else //若播放完毕 自动跳转下一曲 
			{
				int count = m_ctrMusicList.GetItemCount();
				if (m_nowPlayIndex == (count - 1))//音乐边界判断 
				{
					if (check2 == 0)
					{
						if (check1 == 1)
						{
							OnClickedIdstop();
							OnClickedIdplay();
						}
						else
						{
							break;
						}
					}
					else
					{
						int index = m_ctrMusicList.GetNextItem(-1, LVIS_SELECTED);//列表选项判断 
						counter = -index;
						OnClickedIdplay();
					}
				}
				else
				{
					if (check1 == 1)
					{
						OnClickedIdstop();
						OnClickedIdplay();
					}
					else
					{
						OnBnClickedButtonnext();
					}
				}
				
			}
		}
		break;
	default:
		break;
	}
	CDialogEx::OnTimer(nIDEvent);
}


void CMusicPlayerDlg::OnNMCustomdrawSliderstep(NMHDR *pNMHDR, LRESULT *pResult) //进度条滑块响应函数 
{
	LPNMCUSTOMDRAW pNMCD = reinterpret_cast<LPNMCUSTOMDRAW>(pNMHDR);
	// TODO: 在此添加控件通知处理程序代码 
	if (m_PlayStatus == 2)//判断是否处于播放状态
	{
		int pos = m_ctrStep.GetPos();
		if (abs(pos - m_nowPlayStep) > 2)//误差判定 若变化值大于2 视为人工改变进度条 
		{
			m_nowPlayStep = pos;
			TCHAR cmd[MAX_PATH] = L"play";
			TCHAR cmdParam[MAX_PATH] = L"";
			wsprintf(cmdParam, L"%d", m_nowPlayStep * 1000);
			StatusMessageDistribution(cmd, cmdParam, MAX_PATH);//自定义音乐控制消息分配函数 

		}
	}
	*pResult = 0;
}


void CMusicPlayerDlg::OnNMCustomdrawSlidervolume(NMHDR *pNMHDR, LRESULT *pResult)//声音滑块响应函数 
{
	LPNMCUSTOMDRAW pNMCD = reinterpret_cast<LPNMCUSTOMDRAW>(pNMHDR);
	// TODO: 在此添加控件通知处理程序代码 

	int pos = m_ctrVolume.GetPos();
	if (m_PlayStatus == 2)//判断是否出于播放状态 
	{
		if (abs(pos - m_nowPlayVolume) > 2) //误差判定 若变化值大于2 视为人工改变进度条 
		{
			m_nowPlayVolume = pos;
			SetVolume(); //自定义的音量设置函数 

		}
	}
	else
	{
		m_nowPlayVolume = pos;
	}
	m_strVolume.Format(L"%d/100", m_nowPlayVolume);
	UpdateData(FALSE);
	*pResult = 0;
}


int CMusicPlayerDlg::SetVolume()//设置声音 
{
	TCHAR cmd[MAX_PATH] = L"setaudio volume";
	TCHAR cmdParam[MAX_PATH] = L"";
	wsprintf(cmdParam, L"%d", m_nowPlayVolume * 10);
	StatusMessageDistribution(cmd, cmdParam, MAX_PATH);
	return 0;
}

void CMusicPlayerDlg::OnBnClickedButtonnext()//下一曲响应函数
{

	// TODO: 在此添加控件通知处理程序代码 
	nextandlast = 1;
	int count = m_ctrMusicList.GetItemCount();
	if (m_nowPlayIndex < (count - 1))//边界判断 
	{
		counter++;
		m_nowPlayIndex -= 1;
		OnClickedIdplay();
		
	}
	else
	{
		MessageBox(_T("当前已是最后一首音乐!"));
	}
	
}


void CMusicPlayerDlg::OnBnClickedCheck1()
{
	// TODO: 在此添加控件通知处理程序代码
	switch (this->IsDlgButtonChecked(IDC_CHECK1))
	{
	case BST_CHECKED:
		check1 = 1;
		break;
	case BST_UNCHECKED:
		check1 = 0;
		break;
	case BST_INDETERMINATE:
		break;
	}
	if (check1 == 1 && check2 == 1)
	{
		MessageBox(_T("单曲循环和列表循环时无法同时执行!"));
	}
}


void CMusicPlayerDlg::OnBnClickedCheck2()
{
	// TODO: 在此添加控件通知处理程序代码
	switch (this->IsDlgButtonChecked(IDC_CHECK2))
	{
	case BST_CHECKED:
		check2 = 1;
		break;
	case BST_UNCHECKED:
		check2 = 0;
		break;
	case BST_INDETERMINATE:
		break;
	}
	if (check1 == 1 && check2 == 1)
	{
		MessageBox(_T("单曲循环和列表循环时无法同时执行!"));
	}
}


void CMusicPlayerDlg::OnMenured()
{
	SetBackgroundColor(RGB(205, 0, 0));
	// TODO: 在此添加命令处理程序代码
}


void CMusicPlayerDlg::OnMenupurple()
{
	SetBackgroundColor(RGB(147, 112, 219));
	// TODO: 在此添加命令处理程序代码
}


void CMusicPlayerDlg::OnMenuwhite()
{
	SetBackgroundColor(RGB(255, 255, 255));
	// TODO: 在此添加命令处理程序代码
}


void CMusicPlayerDlg::OnMenugray()
{
	SetBackgroundColor(RGB(130, 130, 130));
	// TODO: 在此添加命令处理程序代码
}


void CMusicPlayerDlg::OnMenugreen()
{
	SetBackgroundColor(RGB(175, 238, 238));
	// TODO: 在此添加命令处理程序代码
}


void CMusicPlayerDlg::On32788()
{
	SetBackgroundColor(RGB(175, 238, 238));//OnPaint();
	// TODO: 在此添加命令处理程序代码
}
