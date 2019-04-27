
// MusicPlayerDlg.h : ͷ�ļ� 
// 

#pragma once 
#include "afxwin.h" 
#include "afxcmn.h" 


// CMusicPlayerDlg �Ի��� 
class CMusicPlayerDlg : public CDialogEx
{
	// ���� 
public:
	CMusicPlayerDlg(CWnd* pParent = NULL);// ��׼���캯�� 

										  // �Ի������� 
#ifdef AFX_DESIGN_TIME 
	enum { IDD = IDD_MUSICPLAYER_DIALOG };
#endif 

protected:
	virtual void DoDataExchange(CDataExchange* pDX);// DDX/DDV ֧�� 


													// ʵ�� 
protected:
	HICON m_hIcon;

	// ���ɵ���Ϣӳ�亯�� 
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()

public:
	CString m_strPath;
	CString m_strNowPlayPath;
	afx_msg void OnClickedButtonsel();//��������ļ�

private:
	int m_PlayStatus;// ���ֲ���״̬ 0 ��ʼ״̬ 1 �����ļ��򿪻�ֹͣ״̬ 2 �����ļ�����״̬ 3 �����ļ���ͣ״̬  
	int m_nowPlayIndex = -1;
	long m_nowPlayStep = 0;
	int m_nowPlayLength = 0;
	int m_nowPlayVolume = 50;
	CList<CString, CString&> m_MusicPathList;

	int SaveListToFile();
	int ReadFileToList();
	int SetVolume();
	int GetInfo();
	void AddMusicToList(CString FilePath);
	int StatusMessageDistribution(CString cmd, TCHAR* cmdParam = NULL, int ParamLen = 0);

public:
	CButton m_btnPlay;
	CString m_strVolume;
	CString m_strStep;
	CSliderCtrl m_ctrStep;
	CSliderCtrl m_ctrVolume;
	CListCtrl m_ctrMusicList;
	static int nextandlast;//�����ж���һ����һ����ť�Ƿ񴥷�
	static int counter;//������һ����һ���ļ���
	static int check1;//���ڵ���ѭ���ж�
	static int check2;//�����б�ѭ���ж�

	afx_msg void OnClickedIdplay();//���ֲ���
	afx_msg void OnClickedIdstop();//����ֹͣ
	afx_msg void OnMenuaddfile();//���ļ�
	afx_msg void OnAppAbout();//���ڴ���
	afx_msg void OnMenuexit();//�˳�ϵͳ
	afx_msg void OnLvnItemchangedListmusic(NMHDR *pNMHDR, LRESULT *pResult);//�б�ѡ����ı�ʱ��������Ӧ��Ϣ���� 
	afx_msg void OnDblclkListmusic(NMHDR *pNMHDR, LRESULT *pResult);//������˫����Ӧ���� 
	afx_msg void OnRclickListmusic(NMHDR *pNMHDR, LRESULT *pResult);//
	afx_msg void OnBnClickedButtonnext();//��һ����Ӧ����
	afx_msg void OnBnClickedButtonlast();//��һ����Ӧ����
	afx_msg void OnMenuclear();//��ղ����б�
	afx_msg void OnTimer(UINT_PTR nIDEvent);//��ʱ����Ӧ����
	afx_msg void OnNMCustomdrawSliderstep(NMHDR *pNMHDR, LRESULT *pResult);//������������Ӧ���� 
	afx_msg void OnNMCustomdrawSlidervolume(NMHDR *pNMHDR, LRESULT *pResult);//����������Ӧ���� 
	afx_msg void OnBnClickedCheck1();
	afx_msg void OnBnClickedCheck2();
	afx_msg void OnMenured();
	afx_msg void OnMenupurple();
	afx_msg void OnMenuwhite();
	afx_msg void OnMenugray();
	afx_msg void OnMenugreen();
	afx_msg void On32788();
};