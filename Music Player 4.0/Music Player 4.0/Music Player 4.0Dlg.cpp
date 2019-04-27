
// MusicPlayerDlg.cpp : ʵ���ļ� 
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
// ����Ӧ�ó��򡰹��ڡ��˵���� CAboutDlg �Ի��� 
int CMusicPlayerDlg::nextandlast = 0;
int CMusicPlayerDlg::counter = 0;
int CMusicPlayerDlg::check1 = 0;
int CMusicPlayerDlg::check2 = 0;
class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

	// �Ի������� 
#ifdef AFX_DESIGN_TIME 
	enum { IDD = IDD_ABOUTBOX };
#endif 

protected:
	virtual void DoDataExchange(CDataExchange* pDX); // DDX/DDV ֧�� 

													 // ʵ�� 
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


// CMusicPlayerDlg �Ի��� 



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


// CMusicPlayerDlg ��Ϣ������� 

BOOL CMusicPlayerDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// ��������...���˵�����ӵ�ϵͳ�˵��С� 

	// IDM_ABOUTBOX ������ϵͳ���Χ�ڡ� 
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

	// ���ô˶Ի����ͼ�ꡣ ��Ӧ�ó��������ڲ��ǶԻ���ʱ����ܽ��Զ� 
	// ִ�д˲��� 
	SetIcon(m_hIcon, TRUE);// ���ô�ͼ�� 
	SetIcon(m_hIcon, FALSE);// ����Сͼ�� 

							// TODO: �ڴ���Ӷ���ĳ�ʼ������ 
	m_PlayStatus = 0;
	this->SetBackgroundColor(RGB(175, 238, 238));
	ReadFileToList();
	m_ctrVolume.SetRange(0, 100);
	m_ctrVolume.SetPos(m_nowPlayVolume);
	return TRUE; // ���ǽ��������õ��ؼ������򷵻� TRUE 
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

// �����Ի��������С����ť������Ҫ����Ĵ��� 
// �����Ƹ�ͼ�ꡣ ����ʹ���ĵ�/��ͼģ�͵� MFC Ӧ�ó��� 
// �⽫�ɿ���Զ���ɡ� 

void CMusicPlayerDlg::OnPaint()
{

	if (IsIconic())
	{
		CPaintDC dc(this); // ���ڻ��Ƶ��豸������ 

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// ʹͼ���ڹ����������о��� 
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// ����ͼ�� 
		dc.DrawIcon(x, y, m_hIcon);
	}
	
	else
	{
		CDialogEx::OnPaint();
	}

}

//���û��϶���С������ʱϵͳ���ô˺���ȡ�ù�� 
//��ʾ�� 
HCURSOR CMusicPlayerDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMusicPlayerDlg::OnClickedButtonsel()
{
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	/*
	CFileDialog MFC�е��ļ��򿪱���Ի���CFileDialog�Ĺ��캯��ԭ��Ϊ��

	*/
	//OFN_ALLOWMULTISELECT �����ѡ��־ 
	CFileDialog fdlg(TRUE, _T(".mp3"), NULL, OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT | OFN_ALLOWMULTISELECT, _T("����(.mp3)|*.mp3||"), NULL);
	if (fdlg.DoModal() == IDOK)
	{
		POSITION pos = fdlg.GetStartPosition();
		while (pos != NULL)//������ѡ�ļ��б� 
		{
			//������ GetNextPathName()�����и�BUG�����б���Ŀ����ʱ���ļ�·�����ȡ��������ʧ�� 
			CString FilePath = fdlg.GetNextPathName(pos);//��ȡ�ļ��Ի���ѡ����ļ�ȫ·�� 
														 //MessageBox(FilePath); 
			AddMusicToList(FilePath);
		}

	}
}
void CMusicPlayerDlg::AddMusicToList(CString FilePath)//��װ���� ��������ļ����б���Լ����������� 
{
	CString *path = new CString(FilePath);
	CString name;
	int index = FilePath.ReverseFind('\\');
	if (index > 0)
	{
		name = FilePath.Right(FilePath.GetLength() - index - 1);//��ȡ�ļ����� 
		POSITION pos = m_MusicPathList.AddTail(*path);//����ȫ·����������������� 
		int count = m_ctrMusicList.GetItemCount();
		m_ctrMusicList.InsertItem(count, name);//���������Ʋ����б����� 
		BOOL r = m_ctrMusicList.SetItemData(count, (DWORD_PTR)path);//������ȫ·���б�ؼ��� 
	}
	//MessageBox(name); 
}
int CMusicPlayerDlg::StatusMessageDistribution(CString cmd, TCHAR* cmdParam, int ParamLen)//��װ���� ���ֲ���������Ϣ���亯�� 
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
		if (cmd.Compare(_T("open")) == 0)//���ļ� 
		{
			cmd.Format(_T("open \"%s\" alias music"), cmdParam);
		}
		else if (cmd.Compare(_T("seek")) == 0)//�ļ���λ 
		{
			cmd.Format(_T("seek music to %s"), cmdParam);
		}
		else if (cmd.Compare(_T("setaudio volume")) == 0)//�������� 
		{
			cmd.Format(_T("setaudio music volume to %s"), cmdParam);
		}
		else if (cmd.Compare(_T("play")) == 0)//��λ���� 
		{
			cmd.Format(_T("play music from %s"), cmdParam);
		}
		else if (cmd.Compare(_T("status")) == 0)//��ȡ�ļ����� 
		{
			cmd.Format(_T("status music length"));
			memset(cmdParam, 0, MAX_PATH);
		}
		else
		{
			MessageBox(cmd + "�����޷�ʶ��");
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
		//�����ж���ʾ 
#if _DEBUG 
		TCHAR erStr[MAX_PATH] = _T("");
		mciGetErrorString(r, erStr, MAX_PATH);//����ļ��������������������ʾ��� 
		MessageBox(cmd + erStr);
#endif 
		return -1;
	}
	return -1;
}



void CMusicPlayerDlg::OnClickedIdplay()//���ֲ�����ͣ 
{
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	int index = m_ctrMusicList.GetNextItem(-1, LVIS_SELECTED);//�б�ѡ���ж� 
	//������һ����һ����Ϣ
	if (nextandlast == 1 || check2 == 1|| check1 == 1)
	{
		index += counter;
		nextandlast = 0;
	}
	//����˫��������Ϣ
	if (index >= 0)
	{
		if (index != m_nowPlayIndex && m_PlayStatus > 0)//�ж��Ƿ񲥷��µ������ļ� 
		{
			OnClickedIdstop();//ֹͣ��ǰ�������� 
		}
		m_nowPlayIndex = index;
	}
	if (m_nowPlayIndex < 0) return;
	CString *path = NULL;
	TCHAR cmdPath[MAX_PATH] = _T("");

	//�жϲ���״̬
	switch (m_PlayStatus)
	{
	case 0://�������ļ� 
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
	case 1://�ļ���״̬�������� 
		if (StatusMessageDistribution(_T("play")) == 0)
		{
			
			m_PlayStatus = 2;
			m_strNowPlayPath = m_ctrMusicList.GetItemText(m_nowPlayIndex, 0);
			m_btnPlay.SetWindowTextW(_T("��ͣ"));//���ð�ť�ı���ʾΪ��ͣ 
			UpdateData(FALSE);
			SetTimer(TIMERPLAYSTEP, 1000, NULL);
		}
		break;
	case 2://�ļ�����״̬��ͣ���� 
		if (StatusMessageDistribution(_T("pause")) == 0)
		{
			m_PlayStatus = 3;
			//m_strNowPlayPath = _T("������ͣ������"); 
			m_btnPlay.SetWindowTextW(_T("����"));//���ð�ť�ı���ʾΪ����
											   //UpdateData(FALSE); 
		}
		break;
	case 3:
		if (StatusMessageDistribution(_T("resume")) == 0)
		{
			m_PlayStatus = 2;
			//m_strNowPlayPath = _T("���ڲ��Ÿ�����"); 
			m_btnPlay.SetWindowTextW(_T("��ͣ"));//���ð�ť�ı���ʾΪ��ͣ 
											   //UpdateData(FALSE); 
		}
		break;
	}
}


void CMusicPlayerDlg::OnClickedIdstop()//����ֹͣ 
{
	if (StatusMessageDistribution(_T("close")) == 0)
	{
		KillTimer(TIMERPLAYSTEP);
		m_PlayStatus = 0;//����״̬��ʼ�� 
		//m_nowPlayIndex = -1;//������ų�ʼ�� 
		m_strNowPlayPath = (_T(""));//�ַ�����ʾ��� 
		m_btnPlay.SetWindowTextW(_T("����"));//���ð�ť�ı���ʾΪ����
		//���ý�����
		m_nowPlayStep = 0;
		m_strStep = _T("0:00/0:00");
		UpdateData(FALSE);
	}
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
}


void CMusicPlayerDlg::OnMenuaddfile()//��������ļ��˵���Ӧ���� 
{
	// TODO: �ڴ��������������� 
	OnClickedButtonsel();

}


void CMusicPlayerDlg::OnAppAbout()//���ڲ˵���Ӧ���� 
{
	// TODO: �ڴ��������������� 
	CAboutDlg dlgAbout;
	dlgAbout.DoModal();
}


void CMusicPlayerDlg::OnMenuexit()//�����˳���Ӧ���� 
{
	// TODO: �ڴ��������������� 
	SaveListToFile();
	OnMenuclear();
	PostQuitMessage(0);
	CDialogEx::OnDestroy();
}


void CMusicPlayerDlg::OnLvnItemchangedListmusic(NMHDR *pNMHDR, LRESULT *pResult)//�б�ѡ����ı�ʱ��������Ӧ��Ϣ���� 
{
	LPNMLISTVIEW pNMLV = reinterpret_cast<LPNMLISTVIEW>(pNMHDR);
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	if (m_nowPlayIndex == pNMLV->iItem)
	{
		m_btnPlay.SetWindowText(_T("��ͣ"));
	}
	else
	{
		m_btnPlay.SetWindowText(_T("����"));
	}
	*pResult = 0;
}


void CMusicPlayerDlg::OnDblclkListmusic(NMHDR *pNMHDR, LRESULT *pResult)//������˫����Ӧ���� 
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	counter = 0;
	OnClickedIdplay();
	*pResult = 0;
}


void CMusicPlayerDlg::OnRclickListmusic(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMItemActivate = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	*pResult = 0;
}

void CMusicPlayerDlg::OnBnClickedButtonlast()//��һ����Ӧ����
{
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	nextandlast = 1;
	if (m_nowPlayIndex >0)//���ֱ߽��ж� 
	{
		//m_nowPlayIndex -= 1; 
		counter--;
		OnClickedIdplay();
	}
	else
	{
		MessageBox(_T("��ǰ�Ѵ��ڵ�һ������!"));
	}
}


int CMusicPlayerDlg::SaveListToFile()//�����ļ��б��� 
{
	char* old_locale = _strdup(setlocale(LC_CTYPE, NULL));
	setlocale(LC_CTYPE, "chs");//�趨Ϊ���Ļ��������UNICODE�����������ַ��������� 

	CStdioFile file;
	BOOL r = file.Open(_T("musiclist.dat"), CStdioFile::modeCreate | CStdioFile::modeWrite);
	if (r == FALSE)
	{
		MessageBox(L"�ļ�д��ʧ��!");
		setlocale(LC_CTYPE, old_locale);
		free(old_locale);//��ԭ�����趨 
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
	//�ļ����������󣬻�ԭ 
	setlocale(LC_CTYPE, old_locale);
	free(old_locale);//��ԭ�����趨 
	return 0;
}


int CMusicPlayerDlg::ReadFileToList()//�����ļ��б�ԭ 
{
	//�趨Ϊ���Ļ��������UNICODE�����������ַ��������� 
	char* old_locale = _strdup(setlocale(LC_CTYPE, NULL));
	setlocale(LC_CTYPE, "chs");
	CStdioFile file;
	BOOL r = file.Open(_T("musiclist.dat"), CStdioFile::modeRead);
	if (r == FALSE)
	{
		setlocale(LC_CTYPE, old_locale);
		free(old_locale);//��ԭ�����趨 
		MessageBox(L"�ļ���ȡʧ��!");
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

	//�ļ����������󣬻�ԭ 
	setlocale(LC_CTYPE, old_locale);
	free(old_locale);//��ԭ�����趨 
	return 0;
}


void CMusicPlayerDlg::OnMenuclear()//����б� 
{
	// TODO: �ڴ��������������� 
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


int CMusicPlayerDlg::GetInfo()//��ȡ�����ļ���Ϣ 
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


void CMusicPlayerDlg::OnTimer(UINT_PTR nIDEvent)//��ʱ����Ӧ���� 
{
	// TODO: �ڴ������Ϣ�����������/�����Ĭ��ֵ 
	switch (nIDEvent)
	{
	case TIMERPLAYSTEP://�������仯 
		if (m_PlayStatus == 2)
		{
			if (m_nowPlayStep < m_nowPlayLength)
			{
				m_nowPlayStep += 1;
				m_strStep.Format(L"%2d:%2d/%2d:%2d", m_nowPlayStep / 60, m_nowPlayStep % 60, m_nowPlayLength / 60, m_nowPlayLength % 60);
				m_ctrStep.SetPos(m_nowPlayStep);
				UpdateData(FALSE);
			}
			else //��������� �Զ���ת��һ�� 
			{
				int count = m_ctrMusicList.GetItemCount();
				if (m_nowPlayIndex == (count - 1))//���ֱ߽��ж� 
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
						int index = m_ctrMusicList.GetNextItem(-1, LVIS_SELECTED);//�б�ѡ���ж� 
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


void CMusicPlayerDlg::OnNMCustomdrawSliderstep(NMHDR *pNMHDR, LRESULT *pResult) //������������Ӧ���� 
{
	LPNMCUSTOMDRAW pNMCD = reinterpret_cast<LPNMCUSTOMDRAW>(pNMHDR);
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	if (m_PlayStatus == 2)//�ж��Ƿ��ڲ���״̬
	{
		int pos = m_ctrStep.GetPos();
		if (abs(pos - m_nowPlayStep) > 2)//����ж� ���仯ֵ����2 ��Ϊ�˹��ı������ 
		{
			m_nowPlayStep = pos;
			TCHAR cmd[MAX_PATH] = L"play";
			TCHAR cmdParam[MAX_PATH] = L"";
			wsprintf(cmdParam, L"%d", m_nowPlayStep * 1000);
			StatusMessageDistribution(cmd, cmdParam, MAX_PATH);//�Զ������ֿ�����Ϣ���亯�� 

		}
	}
	*pResult = 0;
}


void CMusicPlayerDlg::OnNMCustomdrawSlidervolume(NMHDR *pNMHDR, LRESULT *pResult)//����������Ӧ���� 
{
	LPNMCUSTOMDRAW pNMCD = reinterpret_cast<LPNMCUSTOMDRAW>(pNMHDR);
	// TODO: �ڴ���ӿؼ�֪ͨ���������� 

	int pos = m_ctrVolume.GetPos();
	if (m_PlayStatus == 2)//�ж��Ƿ���ڲ���״̬ 
	{
		if (abs(pos - m_nowPlayVolume) > 2) //����ж� ���仯ֵ����2 ��Ϊ�˹��ı������ 
		{
			m_nowPlayVolume = pos;
			SetVolume(); //�Զ�����������ú��� 

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


int CMusicPlayerDlg::SetVolume()//�������� 
{
	TCHAR cmd[MAX_PATH] = L"setaudio volume";
	TCHAR cmdParam[MAX_PATH] = L"";
	wsprintf(cmdParam, L"%d", m_nowPlayVolume * 10);
	StatusMessageDistribution(cmd, cmdParam, MAX_PATH);
	return 0;
}

void CMusicPlayerDlg::OnBnClickedButtonnext()//��һ����Ӧ����
{

	// TODO: �ڴ���ӿؼ�֪ͨ���������� 
	nextandlast = 1;
	int count = m_ctrMusicList.GetItemCount();
	if (m_nowPlayIndex < (count - 1))//�߽��ж� 
	{
		counter++;
		m_nowPlayIndex -= 1;
		OnClickedIdplay();
		
	}
	else
	{
		MessageBox(_T("��ǰ�������һ������!"));
	}
	
}


void CMusicPlayerDlg::OnBnClickedCheck1()
{
	// TODO: �ڴ���ӿؼ�֪ͨ����������
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
		MessageBox(_T("����ѭ�����б�ѭ��ʱ�޷�ͬʱִ��!"));
	}
}


void CMusicPlayerDlg::OnBnClickedCheck2()
{
	// TODO: �ڴ���ӿؼ�֪ͨ����������
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
		MessageBox(_T("����ѭ�����б�ѭ��ʱ�޷�ͬʱִ��!"));
	}
}


void CMusicPlayerDlg::OnMenured()
{
	SetBackgroundColor(RGB(205, 0, 0));
	// TODO: �ڴ���������������
}


void CMusicPlayerDlg::OnMenupurple()
{
	SetBackgroundColor(RGB(147, 112, 219));
	// TODO: �ڴ���������������
}


void CMusicPlayerDlg::OnMenuwhite()
{
	SetBackgroundColor(RGB(255, 255, 255));
	// TODO: �ڴ���������������
}


void CMusicPlayerDlg::OnMenugray()
{
	SetBackgroundColor(RGB(130, 130, 130));
	// TODO: �ڴ���������������
}


void CMusicPlayerDlg::OnMenugreen()
{
	SetBackgroundColor(RGB(175, 238, 238));
	// TODO: �ڴ���������������
}


void CMusicPlayerDlg::On32788()
{
	SetBackgroundColor(RGB(175, 238, 238));//OnPaint();
	// TODO: �ڴ���������������
}
