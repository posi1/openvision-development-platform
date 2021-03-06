# Creates the "feed", packages not required for the image
# but that should be built for the feed so that other
# components may use them and install on demand.

# We have a GPLv2 license for this recipe...
require conf/license/openvision-gplv2.inc

# Depend on the image, so that it gets build
DEPENDS = "openvision-enigma2-image"

OPTIONAL_PACKAGES_BROKEN = ""
OPTIONAL_PACKAGES ?= ""

# Get the kernel version, we need it to build conditionally on kernel version.
# NB: this only works in the feed, as the kernel needs to be build before the headers are available.
export KERNEL_VERSION = "${@oe.utils.read_file('${STAGING_KERNEL_BUILDDIR}/kernel-abiversion')}"

OPTIONAL_PACKAGES += "\
	astra-sm \
	autofs \
	autossh \
	ccextractor \
	ccid \
	cdtextinfo \
	cloudflare-dns \
	ctorrent \
	cups \
	davfs2 \
	diffutils \
	djmount \
	dosfstools \
	${@bb.utils.contains("MACHINE_FEATURES", "dreambox", "dreamci", "", d)} \
	dvblast \
	dvbsnoop \
	dvdfs \
	evtest \
	exfat-utils \
	${@bb.utils.contains("MACHINE_FEATURES", "libeplayer", "", "exteplayer3", d)} \
	fuse-exfat \
	${@bb.utils.contains("MACHINE_FEATURES", "sh4stb", "", "gdb", d)} \
	google-dns \
	grep \
	${@bb.utils.contains("MACHINE_FEATURES", "libeplayer", "", "gstplayer", d)} \
	${@bb.utils.contains_any("MACHINE_FEATURES", "hisil libeplayer", "", "gstreamer1.0-libav", d)} \
	hddtemp \
	inadyn-mt \
	inetutils \
	iperf3 \
	iproute2 \
	iptraf \
	iputils \
	joe \
	less \
	libbluray \
	libnss-mdns \
	libsdl2 \
	libudfread \
	${@bb.utils.contains("MACHINE_FEATURES", "sh4stb", "", "lirc", d)} \
	mc \
	mediainfo \
	minisatip \
	mtd-utils \
	mtools \
	nano \
	nbench-byte \
	net-tools \
	ntfs-3g \
	ntp \
	ofgwrite \
	openmultiboot \
	openresolv \
	openssh \
	openvpn \
	picocom \
	ppp \
	procps \
	pv \
	pyload \
	python-beautifulsoup4 \
	python-futures \
	python-iso3166 \
	python-iso639 \
	python-isodate \
	python-js2py \
	python-lxml \
	python-mechanize \
	python-ntplib \
	python-pip \
	python-pyexecjs \
	python-requests \
	python-singledispatch \
	python-websocket-client \
	python-youtube-dl \
	${@bb.utils.contains("TARGET_FPU", "soft", "", "rclone", d)} \
	rsync \
	rtorrent \
	sabnzbd \
	samba \
	satipclient \
	screen \
	sed \
	shellinabox \
	smartmontools \
	sshfs-fuse \
	sshpass \
	strace \
	tcpdump \
	tmux \
	transmission \
	udpxy \
	unrar \
	unzip \
	upx \
	usb-modeswitch \
	usb-modeswitch-data \
	ushare \
	v4l-utils \
	vim \
	wireless-tools \
	wscan \
	yafc \
	zeroconf \
	zip \
	zsh \
	"

OPTIONAL_PACKAGES_remove_sh4 += "\
	rclone \
	"

FIRMWARE_PACKAGES += "\
	firmware-carl9170 \
	firmware-htc7010 \
	firmware-htc9271 \
	firmware-mt7601u \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-rt73 \
	firmware-rt8188fu \
	firmware-rtl8188eu \
	firmware-rtl8192cu \
	firmware-rtl8192cufw \
	firmware-rtl8192eu \
	firmware-rtl8712u \
	firmware-zd1211 \
	"

KERNEL_WIFI_DRIVERS += "\
	${@bb.utils.contains_any("MACHINE", "et13000 sf5008 beyonwizu4 et1x000", "", "kernel-module-ath9k-htc kernel-module-carl9170 kernel-module-r8712u", d)} \
	${@bb.utils.contains_any("MACHINE", "et13000 sf5008 beyonwizu4 et1x000", "", "kernel-module-rtl8187 kernel-module-zd1211rw", d)} \
    "

KERNEL_WIFI_DRIVERS_remove_sh4 += "\
	kernel-module-ath9k-htc \
	kernel-module-carl9170 \
	kernel-module-r8712u \
	kernel-module-rtl8187 \
	kernel-module-zd1211rw \
	"

EXTRA_KERNEL_WIFI_DRIVERS += "\
	${@bb.utils.contains_any("MACHINE", "ventonhdx beyonwizt3 mbtwin sezam5000hd dm500hdv2 dm800sev2 dm7020hd dm7020hdv2 dm8000 dm7080 dm520 dm820 azboxme azboxminime ebox5000 force1 force1plus iqonios100hd iqonios200hd iqonios300hd iqonios300hdv2 mediabox optimussos1plus optimussos1 optimussos2 worldvisionf1plus worldvisionf1 tmtwin tmsingle tmnano tmnano3t tmnano2t tmnano2super tm2t optimussos3plus optimussos2plus ebox5100 ebox7358 eboxlumi ixusszero ixussone maram9 vusolo vuduo vuuno vuultimo gb800se et5x00 et6x00 et9x00 gb800solo gb800ue", "", "kernel-module-r8188eu", d)} \
	${@bb.utils.contains_any("MACHINE", "ixussone ixusszero maram9 et13000 sf5008 beyonwizu4 et1x000", "", "kernel-module-rtl8192cu", d)} \
	"

EXTRA_KERNEL_WIFI_DRIVERS_remove_sh4 += "\
	kernel-module-r8188eu \
	kernel-module-rtl8192cu \
	"

EXTRA_WIFI_DRIVERS += "\
	${@ 'mt7601u' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.2') < 0) else '' } \
	rt3070 \
	rt8188fu \
	rt8723a \
	${@ 'rt8723bs' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '4.12') < 0) else '' } \
	${@bb.utils.contains_any("MACHINE", "dm500hd dm500hdv2 dm800se dm800sev2 dm7020hd dm7020hdv2 dm8000 ixusszero ixussone dm820 dm7080 dm520", "", "rt8812au", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "sh4stb", "", "rt8822bu", d)} \
	${@ 'rtl8188eu' if ("${KERNEL_VERSION}" and bb.utils.vercmp_string("${KERNEL_VERSION}", '3.12') < 0) else '' } \
	${@bb.utils.contains_any("MACHINE", "et5x00 et6x00 et9x00 vuduo vusolo vuuno vuultimo osmio4k osmio4kplus osmini4k dm500hd dm500hdv2 dm800se dm800sev2 dm7020hd dm7020hdv2 dm8000 force1 force1plus iqonios100hd iqonios200hd iqonios300hd iqonios300hdv2 mediabox optimussos1 optimussos1plus optimussos2 optimussos2plus optimussos3plus tm2t tmnano2super tmnano2t tmnano3t tmnano tmsingle tmtwin worldvisionf1 worldvisionf1plus azboxhd azboxme azboxminime maram9 ixusszero ixussone ventonhdx sezam5000hd mbtwin beyonwizt3 gb800ue gb800solo gb800se dm820 dm7080 dm520", "", "rtl8189es", d)} \
	rtl8192cu \
	${@bb.utils.contains_any("MACHINE", "osmio4k osmio4kplus osmini4k", "", "rt8814au rtl8192eu", d)} \
	"

EXTRA_WIFI_DRIVERS_remove_sh4 += "\
	rt8814au \
	rtl8189es \
	"

ENIGMA2_OPTIONAL += "\
	channelsettings-enigma2-meta \
	dvb-usb-drivers-meta \
	${@bb.utils.contains_any("MACHINE_FEATURES", "bwlcd96 bwlcd128 bwlcd140 bwlcd255 colorlcd220 colorlcd390 colorlcd400 colorlcd480 colorlcd720 colorlcd800", "enigma2-display-skins", "", d)} \
	${@bb.utils.contains("EXTRA_IMAGEDEPENDS", "vuplus-tuner-turbo", "enigma2-plugin-drivers-dvb-usb-turbo", "", d)} \
	enigma2-plugin-drivers-usbserial \
	enigma2-plugin-extensions-arabicsavior \
	enigma2-plugin-extensions-automatic-fullbackup \
	enigma2-plugin-extensions-backupsuite \
	enigma2-plugin-extensions-bhweather \
	enigma2-plugin-extensions-blurayplayer \
	enigma2-plugin-extensions-dlnabrowser \
	enigma2-plugin-extensions-dlnaserver \
	enigma2-plugin-extensions-dreamplex \
	enigma2-plugin-extensions-e2iplayer \
	enigma2-plugin-extensions-epgimport \
	enigma2-plugin-extensions-fontinfo \
	enigma2-plugin-extensions-hdmitest \
	enigma2-plugin-extensions-historyzapselector \
	${@bb.utils.contains("MACHINE_FEATURES", "libeplayer", "", "enigma2-plugin-extensions-install-exteplayer3", d)} \
	enigma2-plugin-extensions-install-ffmpeg \
	${@bb.utils.contains("MACHINE_FEATURES", "libeplayer", "", "enigma2-plugin-extensions-install-gstplayer", d)} \
	enigma2-plugin-extensions-keyadder \
	${@bb.utils.contains_any("MACHINE_FEATURES", "azbox", "enigma2-plugin-extensions-keymapconfig enigma2-plugin-extensions-rsiconfig enigma2-plugin-extensions-rsimediacenter enigma2-plugin-systemplugins-ofwlauncher enigma2-plugin-extensions-aziptv enigma2-plugin-extensions-azplay", "", d)} \
	${@bb.utils.contains_any("MACHINE_FEATURES", "colorlcd colorlcd220 colorlcd390 colorlcd400 colorlcd480 colorlcd720 colorlcd800 bwlcd140 bwlcd255", "enigma2-plugin-extensions-lcd4linux", "", d)} \
	enigma2-plugin-extensions-managerautofs \
	enigma2-plugin-extensions-merlininfo \
	enigma2-plugin-extensions-modifyplifullhd \
	enigma2-plugin-extensions-moviemanager \
	enigma2-plugin-extensions-openmultiboot \
	enigma2-plugin-extensions-openvisionskintools \
	enigma2-plugin-extensions-raedquicksignal \
	enigma2-plugin-extensions-refreshbouquet \
	enigma2-plugin-extensions-sdgradio \
	enigma2-plugin-extensions-tmbd \
	enigma2-plugin-extensions-vcs \
	enigma2-plugin-extensions-weathermsn \
	enigma2-plugin-extensions-xmodem \
	enigma2-plugin-extensions-yahooweather \
	enigma2-plugin-extensions-youtube \
	enigma2-plugin-security-firewall \
	enigma2-plugin-skins-arctic-raed \
	enigma2-plugin-skins-atilehd-raed \
	enigma2-plugin-skins-blacktransfhd-raed \
	enigma2-plugin-skins-bundesligafhd-raed \
	enigma2-plugin-skins-cinogripli \
	enigma2-plugin-skins-dreamplexskins \
	enigma2-plugin-skins-glamouraurafhd \
	enigma2-plugin-skins-hdlinesuper-raed \
	enigma2-plugin-skins-iflatfhd \
	enigma2-plugin-skins-kravenfhd \
	enigma2-plugin-skins-kravenhd \
	enigma2-plugin-skins-kravenvb \
	enigma2-plugin-skins-maxfhdxta-raed \
	enigma2-plugin-skins-metrix-vision \
	enigma2-plugin-skins-multibox-raed \
	enigma2-plugin-skins-mxblack-raed \
	enigma2-plugin-skins-mxhq9b-raed \
	enigma2-plugin-skins-mx-hq7 \
	enigma2-plugin-skins-mx-hq9w \
	enigma2-plugin-skins-mxsline-raed \
	enigma2-plugin-skins-octetfhd \
	enigma2-plugin-skins-openvix \
	enigma2-plugin-skins-pli-hd-fullnight \
	enigma2-plugin-skins-sevenhd \
	enigma2-plugin-skins-simple-gray-hd \
	enigma2-plugin-skins-turbo-raed \
	enigma2-plugin-skins-turquoisehd \
	enigma2-plugin-skins-universehd \
	enigma2-plugin-skins-xionhdf \
	enigma2-plugin-skins-whitetransfhd-raed \
	enigma2-plugin-skins-wowcataclysmfhd-raed \
	enigma2-plugin-systemplugins-crossepg \
	enigma2-plugin-systemplugins-extnumberzap \
	enigma2-plugin-systemplugins-extrafancontrol \
	enigma2-plugin-systemplugins-hrtunerproxy \
	${@bb.utils.contains("MACHINE_FEATURES", "micom", "enigma2-plugin-systemplugins-micomupgrade" , "", d)} \
	enigma2-plugin-systemplugins-mountmanager \
	enigma2-plugin-systemplugins-netspeedtest \
	enigma2-plugin-systemplugins-newvirtualkeyboard \
	enigma2-plugin-systemplugins-radiotimesemulator \
	${@bb.utils.contains("MACHINE_FEATURES", "libeplayer", "", "enigma2-plugin-systemplugins-serviceapp", d)} \
	enigma2-plugin-systemplugins-signalfinder \
	enigma2-plugin-systemplugins-quadpip \
	enigma2-plugins \
	meta-enigma2-dvdburn \
	openvision-core-plugin \
	packagegroup-openplugins \
	picons-enigma2-meta \
	softcams-enigma2-meta \
	"

DEPENDS += "${ENIGMA2_OPTIONAL} ${EXTRA_WIFI_DRIVERS} ${FIRMWARE_PACKAGES} ${OPTIONAL_PACKAGES} enigma2-2boom-plugins"
