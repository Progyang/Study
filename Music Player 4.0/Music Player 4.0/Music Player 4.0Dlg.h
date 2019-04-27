
// MusicPlayerDlg.h : 头文件 
// 

#pragma once 
#include "afxwin.h" 
#include "afxcmn.h" 


// CMusicPlayerDlg 对话框 
class CMusicPlayerDlg : public CDialogEx
{
	// 构造 
public:
	CMusicPlayerDlg(CWnd* pParent = NULL);// 标准构造函数 

										  // 对话框数据 
#ifdef AFX_DESIGN_TIME 
	enum { IDD = IDD_MUSICPLAYER_DIALOG };
#endif 

protected:
	virtual void DoDataExchange(CDataExchange* pDX);// DDX/DDV 支持 


													// 实现 
protected:
	HICON m_hIcon;

	// 生成的消息映射函数 
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()

public:
	CString m_strPath;
	CString m_strNowPlayPath;
	afx_msg void OnClickedButtonsel();//添加音乐文件

private:
	int m_PlayStatus;// 音乐播放状态 0 初始状态 1 音乐文件打开或停止状态 2 音乐文件播放状态 3 音乐文件暂停状态  
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
	static int nextandlast;//用于判断上一曲下一曲按钮是否触发
	static int counter;//用于上一曲下一曲的计数
	static int check1;//用于单曲循环判断
	static int check2;//用于列表循环判断

	afx_msg void OnClickedIdplay();//音乐播放
	afx_msg void OnClickedIdstop();//播放停止
	afx_msg void OnMenuaddfile();//打开文件
	afx_msg void OnAppAbout();//关于窗口
	afx_msg void OnMenuexit();//退出系统
	afx_msg void OnLvnItemchangedListmusic(NMHDR *pNMHDR, LRESULT *pResult);//列表选中项改变时触发的响应消息函数 
	afx_msg void OnDblclkListmusic(NMHDR *pNMHDR, LRESULT *pResult);//鼠标左键双击响应函数 
	afx_msg void OnRclickListmusic(NMHDR *pNMHDR, LRESULT *pResult);//
	afx_msg void OnBnClickedButtonnext();//下一曲响应函数
	afx_msg void OnBnClickedButtonlast();//上一曲响应函数
	afx_msg void OnMenuclear();//清空播放列表
	afx_msg void OnTimer(UINT_PTR nIDEvent);//定时器响应函数
	afx_msg void OnNMCustomdrawSliderstep(NMHDR *pNMHDR, LRESULT *pResult);//进度条滑块响应函数 
	afx_msg void OnNMCustomdrawSlidervolume(NMHDR *pNMHDR, LRESULT *pResult);//声音滑块响应函数 
	afx_msg void OnBnClickedCheck1();
	afx_msg void OnBnClickedCheck2();
	afx_msg void OnMenured();
	afx_msg void OnMenupurple();
	afx_msg void OnMenuwhite();
	afx_msg void OnMenugray();
	afx_msg void OnMenugreen();
	afx_msg void On32788();
};